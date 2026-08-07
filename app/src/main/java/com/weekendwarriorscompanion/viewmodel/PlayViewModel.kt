package com.weekendwarriorscompanion.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.weekendwarriorscompanion.model.Character

class PlayViewModel : ViewModel() {
    var warriors by mutableStateOf(listOf<Character>())
    var enemies by mutableStateOf(listOf<Character>())
    var selectedCharacter by mutableStateOf<Character?>(null)

    fun setupBattle(warriors: List<Character>, enemies: List<Character>) {
        this.warriors = warriors
        this.enemies = enemies
        selectedCharacter = warriors.firstOrNull() ?: enemies.firstOrNull()
    }

    fun selectCharacter(character: Character) {
        selectedCharacter = character
    }
}
