package com.weekendwarriorscompanion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weekendwarriorscompanion.viewmodel.PlayViewModel

import com.weekendwarriorscompanion.ui.theme.WarriorBlue
import com.weekendwarriorscompanion.ui.theme.WarriorDarkBlue
import com.weekendwarriorscompanion.ui.theme.WarriorWhite

@Composable
fun PlayScreen(
    viewModel: PlayViewModel,
    onEdit: () -> Unit,
    onQuit: () -> Unit
) {
    val selectedChar = viewModel.selectedCharacter
    
    Row(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        // Sidebar
        Column(
            Modifier
                .width(110.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                .padding(4.dp)
        ) {
            Text(
                "WARRIORS", 
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelSmall, 
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            LazyColumn(Modifier.weight(1f)) {
                items(viewModel.warriors) { char ->
                    Button(
                        onClick = { viewModel.selectCharacter(char) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        contentPadding = PaddingValues(4.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = if (selectedChar == char) 
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) 
                            else ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        CharacterPortrait(char.portraitPath, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(char.name.take(6), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
            Text(
                "ENEMIES", 
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelSmall, 
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB04C4C) // A reddish tone for enemies
            )
            LazyColumn(Modifier.weight(1f)) {
                items(viewModel.enemies) { char ->
                    Button(
                        onClick = { viewModel.selectCharacter(char) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        contentPadding = PaddingValues(4.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = if (selectedChar == char) 
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) 
                            else ButtonDefaults.buttonColors(containerColor = Color(0xFFB04C4C))
                    ) {
                        CharacterPortrait(char.portraitPath, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(char.name.take(6), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            Button(
                onClick = onEdit, 
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp), 
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.onTertiary)
            ) { 
                Text("EDIT", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) 
            }
            Button(
                onClick = onQuit, 
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp), 
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) { 
                Text("QUIT", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) 
            }
        }
        
        VerticalDivider(color = MaterialTheme.colorScheme.secondary)
        
        // Main sheet
        Column(
            Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            selectedChar?.let { char ->
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        char.name.uppercase(), 
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.headlineMedium, 
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
                Text(
                    "${char.role} | ${char.skill}", 
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(Modifier.height(8.dp))
                
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(8.dp), 
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatBox("ACT", char.action.toString())
                    StatBox("MOVE", char.movement)
                    StatBox("REACT", char.reaction.toString())
                    StatBox("ARMOR", char.armor.toString())
                    StatBox("HEARTS", char.hearts.toString())
                }
                
                Spacer(Modifier.height(16.dp))
                Text("WEAPONS", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                char.weapons.forEach { weapon ->
                    WeaponDisplayRow(weapon)
                }
                
                Spacer(Modifier.height(16.dp))
                Text("ABILITIES", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                Surface(
                    color = Color.White.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(char.abilities, modifier = Modifier.padding(12.dp))
                }
            } ?: Text("SELECT A CHARACTER", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
fun WeaponDisplayRow(weapon: com.weekendwarriorscompanion.model.Weapon) {
    Card(
        Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = WarriorWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(weapon.name.ifBlank { "UNNAMED WEAPON" }.uppercase(), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black, color = WarriorBlue)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatSmall("ACT", weapon.action.toString())
                StatSmall("RNG", weapon.range)
                StatSmall("ATK", weapon.attacks.toString())
                StatSmall("POW", weapon.power.toString())
                StatSmall("DMG", weapon.damage.toString())
            }
        }
    }
}

@Composable
fun StatSmall(label: String, value: String) {
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatBox(label: String, value: String) {
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = WarriorBlue)
        Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black, color = WarriorDarkBlue)
    }
}
