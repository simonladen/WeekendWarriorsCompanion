package com.weekendwarriorscompanion.repository

import com.weekendwarriorscompanion.model.Character
import com.weekendwarriorscompanion.storage.CharacterFileStorage
import java.util.UUID

class CharacterRepository(private val storage: CharacterFileStorage) {
    private var roster = mutableListOf<Character>()

    init {
        loadRoster()
    }

    private fun loadRoster() {
        roster = storage.loadCharacters()
    }

    private fun saveRoster() {
        storage.saveCharacters(roster)
    }

    fun getCharacters(): List<Character> = roster

    fun addCharacter(character: Character) {
        roster.add(character)
        saveRoster()
    }

    fun updateCharacter(character: Character) {
        val index = roster.indexOfFirst { it.id == character.id }
        if (index != -1) {
            roster[index] = character
            saveRoster()
        }
    }

    fun removeCharacter(id: UUID) {
        removeCharacters(listOf(id))
    }

    fun removeCharacters(ids: List<UUID>) {
        val iterator = roster.iterator()
        while (iterator.hasNext()) {
            if (ids.contains(iterator.next().id)) {
                iterator.remove()
            }
        }
        saveRoster()
    }
}
