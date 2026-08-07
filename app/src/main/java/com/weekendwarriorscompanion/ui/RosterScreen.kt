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
fun RosterScreen(
    roster: List<Character>,
    title: String,
    multiSelect: Boolean = false,
    onCharacterSelected: (Character) -> Unit = {},
    onCharactersAction: (List<Character>) -> Unit = {},
    onBack: () -> Unit
) {
    val selectedCharacters = remember { mutableStateListOf<Character>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title.uppercase(), fontWeight = FontWeight.Black) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WarriorOrange,
                    titleContentColor = Color.White
                ),
                actions = {
                    if (multiSelect && selectedCharacters.isNotEmpty()) {
                        Button(
                            onClick = { onCharactersAction(selectedCharacters.toList()) },
                            colors = ButtonDefaults.buttonColors(containerColor = WarriorWhite, contentColor = WarriorOrange),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("CONFIRM", fontWeight = FontWeight.Bold)
                        }
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
        ) {
            LazyColumn(Modifier.weight(1f).padding(8.dp)) {
                items(roster) { char ->
                    val isSelected = selectedCharacters.contains(char)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                if (multiSelect) {
                                    if (isSelected) selectedCharacters.remove(char) else selectedCharacters.add(char)
                                } else {
                                    onCharacterSelected(char)
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) WarriorOrange.copy(alpha = 0.1f) else WarriorWhite
                        ),
                        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 2.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ListItem(
                            leadingContent = { CharacterPortrait(char.portraitPath) },
                            headlineContent = { Text(char.name.uppercase(), fontWeight = FontWeight.Bold, color = WarriorBlue) },
                            supportingContent = { Text(char.role, color = WarriorBlue.copy(alpha = 0.7f)) },
                            trailingContent = {
                                if (multiSelect) {
                                    Checkbox(
                                        checked = isSelected,
                                        onCheckedChange = null,
                                        colors = CheckboxDefaults.colors(checkedColor = WarriorOrange)
                                    )
                                }
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
            }
            Button(
                onClick = onBack, 
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarriorBlue)
            ) {
                Text("BACK", fontWeight = FontWeight.Bold)
            }
        }
    }
}
