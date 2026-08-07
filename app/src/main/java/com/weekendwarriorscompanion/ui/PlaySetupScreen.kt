package com.weekendwarriorscompanion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weekendwarriorscompanion.model.Character
import com.weekendwarriorscompanion.ui.theme.WarriorBlue
import com.weekendwarriorscompanion.ui.theme.WarriorOrange
import com.weekendwarriorscompanion.ui.theme.WarriorWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySetupScreen(
    roster: List<Character>,
    onStartPlay: (warriors: List<Character>, enemies: List<Character>) -> Unit,
    onBack: () -> Unit
) {
    val selectedWarriors = remember { mutableStateListOf<Character>() }
    val selectedEnemies = remember { mutableStateListOf<Character>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PLAY SETUP", fontWeight = FontWeight.Black) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WarriorOrange,
                    titleContentColor = Color.White
                ),
                actions = {
                    Button(
                        onClick = { onStartPlay(selectedWarriors, selectedEnemies) },
                        colors = ButtonDefaults.buttonColors(containerColor = WarriorWhite, contentColor = WarriorOrange),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("START", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text("SELECT WARRIORS (${selectedWarriors.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = WarriorOrange)
            LazyColumn(Modifier.weight(1f).padding(vertical = 8.dp)) {
                items(roster) { char ->
                    val isSelected = selectedWarriors.contains(char)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) WarriorOrange.copy(alpha = 0.1f) else WarriorWhite),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 1.dp)
                    ) {
                        ListItem(
                            modifier = Modifier.clickable {
                                if (isSelected) selectedWarriors.remove(char) else selectedWarriors.add(char)
                            },
                            leadingContent = { CharacterPortrait(char.portraitPath) },
                            headlineContent = { Text(char.name.uppercase(), fontWeight = FontWeight.Bold) },
                            trailingContent = {
                                Checkbox(
                                    checked = isSelected, 
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(checkedColor = WarriorOrange)
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text("SELECT ENEMIES (${selectedEnemies.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color(0xFFB04C4C))
            LazyColumn(Modifier.weight(1f).padding(vertical = 8.dp)) {
                items(roster) { char ->
                    val isSelected = selectedEnemies.contains(char)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFB04C4C).copy(alpha = 0.1f) else WarriorWhite),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 1.dp)
                    ) {
                        ListItem(
                            modifier = Modifier.clickable {
                                if (isSelected) selectedEnemies.remove(char) else selectedEnemies.add(char)
                            },
                            leadingContent = { CharacterPortrait(char.portraitPath) },
                            headlineContent = { Text(char.name.uppercase(), fontWeight = FontWeight.Bold) },
                            trailingContent = {
                                Checkbox(
                                    checked = isSelected, 
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFFB04C4C))
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
            }
            
            Button(
                onClick = onBack, 
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarriorBlue)
            ) {
                Text("BACK", fontWeight = FontWeight.Bold)
            }
        }
    }
}
