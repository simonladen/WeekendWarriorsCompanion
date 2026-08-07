package com.weekendwarriorscompanion.ui

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
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
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            val contentModifier = Modifier.weight(1f)

            if (isLandscape) {
                Row(modifier = contentModifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        WarriorSelectionSection(roster, selectedWarriors)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        EnemySelectionSection(roster, selectedEnemies)
                    }
                }
            } else {
                Column(modifier = contentModifier) {
                    WarriorSelectionSection(roster, selectedWarriors)
                    Spacer(Modifier.height(16.dp))
                    EnemySelectionSection(roster, selectedEnemies)
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

@Composable
private fun ColumnScope.WarriorSelectionSection(
    roster: List<Character>,
    selectedWarriors: MutableList<Character>
) {
    Text(
        "SELECT WARRIORS (${selectedWarriors.size})",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Black,
        color = WarriorOrange
    )
    LazyColumn(Modifier.weight(1f).padding(vertical = 8.dp)) {
        items(roster) { char ->
            val isSelected = selectedWarriors.contains(char)
            CharacterSelectionCard(
                char = char,
                isSelected = isSelected,
                selectedColor = WarriorOrange,
                onToggle = {
                    if (isSelected) selectedWarriors.remove(char) else selectedWarriors.add(char)
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.EnemySelectionSection(
    roster: List<Character>,
    selectedEnemies: MutableList<Character>
) {
    Text(
        "SELECT ENEMIES (${selectedEnemies.size})",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Black,
        color = Color(0xFFB04C4C)
    )
    LazyColumn(Modifier.weight(1f).padding(vertical = 8.dp)) {
        items(roster) { char ->
            val isSelected = selectedEnemies.contains(char)
            CharacterSelectionCard(
                char = char,
                isSelected = isSelected,
                selectedColor = Color(0xFFB04C4C),
                onToggle = {
                    if (isSelected) selectedEnemies.remove(char) else selectedEnemies.add(char)
                }
            )
        }
    }
}

@Composable
private fun CharacterSelectionCard(
    char: Character,
    isSelected: Boolean,
    selectedColor: Color,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) selectedColor.copy(alpha = 0.1f) else WarriorWhite),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 1.dp)
    ) {
        ListItem(
            modifier = Modifier.clickable { onToggle() },
            leadingContent = { CharacterPortrait(char.portraitPath) },
            headlineContent = { Text(char.name.uppercase(), fontWeight = FontWeight.Bold) },
            trailingContent = {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(checkedColor = selectedColor)
                )
            },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
    }
}
