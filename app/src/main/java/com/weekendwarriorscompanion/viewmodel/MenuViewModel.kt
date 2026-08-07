package com.weekendwarriorscompanion.viewmodel

import androidx.lifecycle.ViewModel
import com.weekendwarriorscompanion.model.Character
import com.weekendwarriorscompanion.repository.CharacterRepository
import java.util.UUID

class MenuViewModel(private val repository: CharacterRepository) : ViewModel() {
    val roster: List<Character> get() = repository.getCharacters()

    fun addCharacter(character: Character) {
        repository.addCharacter(character)
    }

    fun updateCharacter(character: Character) {
        repository.updateCharacter(character)
    }

    fun removeCharacter(id: UUID) {
        repository.removeCharacter(id)
    }

    fun removeCharacters(ids: List<UUID>) {
        repository.removeCharacters(ids)
    }
}
