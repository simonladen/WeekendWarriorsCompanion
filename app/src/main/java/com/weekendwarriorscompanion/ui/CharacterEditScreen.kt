package com.weekendwarriorscompanion.ui

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.weekendwarriorscompanion.model.Character
import com.weekendwarriorscompanion.model.Weapon
import com.weekendwarriorscompanion.storage.PortraitStorage
import com.weekendwarriorscompanion.ui.theme.WarriorBlue
import com.weekendwarriorscompanion.ui.theme.WarriorOrange
import com.weekendwarriorscompanion.ui.theme.WarriorWhite
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterEditScreen(
    character: Character? = null,
    onSave: (Character) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val portraitStorage = remember { PortraitStorage(context) }
    
    var name by remember { mutableStateOf(character?.name ?: "") }
    var role by remember { mutableStateOf(character?.role ?: "") }
    var skill by remember { mutableStateOf(character?.skill ?: "") }
    
    var action by remember { mutableStateOf(character?.action?.let { if (it == 0) "" else it.toString() } ?: "") }
    var movement by remember { mutableStateOf(character?.movement ?: "") }
    var reaction by remember { mutableStateOf(character?.reaction?.let { if (it == 0) "" else it.toString() } ?: "") }
    var armor by remember { mutableStateOf(character?.armor?.let { if (it == 0) "" else it.toString() } ?: "") }
    var hearts by remember { mutableStateOf(character?.hearts?.let { if (it == 0) "" else it.toString() } ?: "") }
    
    var abilities by remember { mutableStateOf(character?.abilities ?: "") }
    var portraitPath by remember { mutableStateOf(character?.portraitPath ?: "") }
    
    val weapons = remember { 
        mutableStateListOf<Weapon>().apply {
            addAll(character?.weapons ?: listOf(Weapon(), Weapon()))
        }
    }

    var showImageSourceDialog by remember { mutableStateOf(false) }
    var pendingImageUri by remember { mutableStateOf<Uri?>(null) }
    var showCropScreen by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            pendingImageUri = uri
            showCropScreen = true
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            showCropScreen = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = File(context.cacheDir, "temp_image.jpg")
            if (file.exists()) file.delete()
            file.createNewFile()
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            pendingImageUri = uri
            try {
                cameraLauncher.launch(uri)
            } catch (e: Exception) {
                Toast.makeText(context, "Could not open camera", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    if (showCropScreen && pendingImageUri != null) {
        ImageCropScreen(
            imageUri = pendingImageUri!!,
            onImageCropped = { bitmap ->
                val path = portraitStorage.savePortrait(bitmap)
                portraitPath = path
                showCropScreen = false
            },
            onCancel = { showCropScreen = false }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text((if (character == null) "CREATE CHARACTER" else "EDIT CHARACTER"), fontWeight = FontWeight.Black) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = WarriorOrange,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        Button(
                            onClick = {
                                val newChar = (character ?: Character()).copy(
                                    name = name,
                                    role = role,
                                    skill = skill,
                                    action = action.toIntOrNull() ?: 0,
                                    movement = movement,
                                    reaction = reaction.toIntOrNull() ?: 0,
                                    armor = armor.toIntOrNull() ?: 0,
                                    hearts = hearts.toIntOrNull() ?: 0,
                                    abilities = abilities,
                                    weapons = weapons.toMutableList(),
                                    portraitPath = portraitPath
                                )
                                onSave(newChar)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WarriorWhite, contentColor = WarriorOrange),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("SAVE", fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Portrait selection circle
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(WarriorBlue.copy(alpha = 0.3f))
                        .clickable { showImageSourceDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    if (portraitPath.isNotEmpty()) {
                        CharacterPortrait(portraitPath, modifier = Modifier.fillMaxSize())
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AddAPhoto, contentDescription = "Add Portrait", tint = WarriorBlue)
                            Text("PORTRAIT", style = MaterialTheme.typography.labelSmall, color = WarriorBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(Modifier.height(24.dp))
                
                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WarriorOrange,
                    unfocusedBorderColor = WarriorBlue,
                    focusedLabelColor = WarriorOrange,
                    unfocusedLabelColor = WarriorBlue,
                    cursorColor = WarriorOrange
                )

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("NAME") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
                OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("ROLE") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
                OutlinedTextField(value = skill, onValueChange = { skill = it }, label = { Text("SKILL") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
                
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = action,
                        onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) action = it },
                        label = { Text("ACTION") },
                        placeholder = { Text("0") },
                        modifier = Modifier.weight(1f),
                        colors = textFieldColors
                    )
                    OutlinedTextField(value = movement, onValueChange = { movement = it }, label = { Text("MOVE") }, modifier = Modifier.weight(1f), colors = textFieldColors)
                }
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = reaction,
                        onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) reaction = it },
                        label = { Text("REACT") },
                        placeholder = { Text("0") },
                        modifier = Modifier.weight(1f),
                        colors = textFieldColors
                    )
                    OutlinedTextField(
                        value = armor,
                        onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) armor = it },
                        label = { Text("ARMOR") },
                        placeholder = { Text("0") },
                        modifier = Modifier.weight(1f),
                        colors = textFieldColors
                    )
                    OutlinedTextField(
                        value = hearts,
                        onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) hearts = it },
                        label = { Text("HEARTS") },
                        placeholder = { Text("0") },
                        modifier = Modifier.weight(1f),
                        colors = textFieldColors
                    )
                }
                
                Spacer(Modifier.height(24.dp))
                Text("WEAPONS", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = WarriorOrange, modifier = Modifier.align(Alignment.Start))
                weapons.forEachIndexed { index, weapon ->
                    WeaponEditRow(weapon = weapon, onUpdate = { weapons[index] = it })
                }
                Button(
                    onClick = { weapons.add(Weapon()) }, 
                    modifier = Modifier.padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WarriorBlue),
                    shape = RoundedCornerShape(4.dp)
                ) { 
                    Text("ADD WEAPON", fontWeight = FontWeight.Bold) 
                }
                
                Spacer(Modifier.height(24.dp))
                OutlinedTextField(
                    value = abilities,
                    onValueChange = { abilities = it },
                    label = { Text("ABILITIES") },
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    minLines = 5,
                    colors = textFieldColors
                )
                
                Button(
                    onClick = onCancel, 
                    modifier = Modifier.padding(top = 24.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("CANCEL", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("CHOOSE PORTRAIT SOURCE") },
            text = { Text("Would you like to take a new photo or pick one from your gallery?") },
            confirmButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                }) {
                    Text("CAMERA")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    galleryLauncher.launch("image/*")
                    showImageSourceDialog = false
                }) {
                    Text("GALLERY")
                }
            }
        )
    }
}

@Composable
fun WeaponEditRow(weapon: Weapon, onUpdate: (Weapon) -> Unit) {
    Card(
        Modifier.padding(vertical = 8.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = WarriorWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WarriorOrange,
                unfocusedBorderColor = WarriorBlue.copy(alpha = 0.5f)
            )
            OutlinedTextField(
                value = weapon.name,
                onValueChange = { onUpdate(weapon.copy(name = it)) },
                label = { Text("WEAPON NAME") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )
            Row {
                OutlinedTextField(
                    value = if (weapon.action == 0) "" else weapon.action.toString(),
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) onUpdate(weapon.copy(action = it.toIntOrNull() ?: 0)) },
                    label = { Text("ACT") },
                    placeholder = { Text("0") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors
                )
                OutlinedTextField(value = weapon.range, onValueChange = { onUpdate(weapon.copy(range = it)) }, label = { Text("RNG") }, modifier = Modifier.weight(1f), colors = textFieldColors)
                OutlinedTextField(
                    value = if (weapon.attacks == 0) "" else weapon.attacks.toString(),
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) onUpdate(weapon.copy(attacks = it.toIntOrNull() ?: 0)) },
                    label = { Text("ATK") },
                    placeholder = { Text("0") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors
                )
            }
            Row {
                OutlinedTextField(
                    value = if (weapon.power == 0) "" else weapon.power.toString(),
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) onUpdate(weapon.copy(power = it.toIntOrNull() ?: 0)) },
                    label = { Text("POW") },
                    placeholder = { Text("0") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = if (weapon.damage == 0) "" else weapon.damage.toString(),
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) onUpdate(weapon.copy(damage = it.toIntOrNull() ?: 0)) },
                    label = { Text("DMG") },
                    placeholder = { Text("0") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors
                )
            }
        }
    }
}
