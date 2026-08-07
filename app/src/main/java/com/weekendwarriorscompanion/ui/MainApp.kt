package com.weekendwarriorscompanion.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weekendwarriorscompanion.viewmodel.MenuViewModel
import com.weekendwarriorscompanion.viewmodel.PlayViewModel
import androidx.compose.runtime.remember
import android.content.Context
import com.weekendwarriorscompanion.storage.CharacterFileStorage
import com.weekendwarriorscompanion.repository.CharacterRepository
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text

@Composable
fun MainApp(context: Context) {
    val navController = rememberNavController()

    val storage = remember { CharacterFileStorage(context) }
    val repository = remember { CharacterRepository(storage) }
    val menuViewModel: MenuViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(repository) as T
        }
    })
    val playViewModel: PlayViewModel = viewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") {
                MenuScreen(
                    onCreateCharacter = { navController.navigate("create_character") },
                    onEditCharacter = { navController.navigate("roster_edit") },
                    onRemoveCharacter = { navController.navigate("roster_remove") },
                    onPlay = { navController.navigate("play_setup") }
                )
            }
            composable("create_character") {
                CharacterEditScreen(
                    onSave = {
                        menuViewModel.addCharacter(it)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
            composable("roster_edit") {
                RosterScreen(
                    roster = menuViewModel.roster,
                    title = "Select Character to Edit",
                    onCharacterSelected = { char ->
                        navController.navigate("edit_character/${char.id}")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("edit_character/{charId}") { backStackEntry ->
                val charId = backStackEntry.arguments?.getString("charId")
                val character = menuViewModel.roster.find { it.id.toString() == charId }
                CharacterEditScreen(
                    character = character,
                    onSave = {
                        menuViewModel.updateCharacter(it)
                        navController.popBackStack("menu", inclusive = false)
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
            composable("roster_remove") {
                RosterScreen(
                    roster = menuViewModel.roster,
                    title = "Select Characters to Remove",
                    multiSelect = true,
                    onCharactersAction = { chars ->
                        menuViewModel.removeCharacters(chars.map { it.id })
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("play_setup") {
                PlaySetupScreen(
                    roster = menuViewModel.roster,
                    onStartPlay = { warriors, enemies ->
                        playViewModel.setupBattle(warriors, enemies)
                        navController.navigate("play")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("play") {
                PlayScreen(
                    viewModel = playViewModel,
                    onEdit = { navController.navigate("play_setup") },
                    onQuit = { navController.popBackStack("menu", inclusive = false) }
                )
            }
        }
    }
}
