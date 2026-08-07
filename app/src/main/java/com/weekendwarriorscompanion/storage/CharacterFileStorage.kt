package com.weekendwarriorscompanion.storage

import android.content.Context
import com.weekendwarriorscompanion.model.Character
import com.weekendwarriorscompanion.model.Weapon
import java.io.File
import java.util.UUID

class CharacterFileStorage(private val context: Context) {
    private val fileName = "characters.txt"
    private val file = File(context.filesDir, fileName)

    fun saveCharacters(characters: List<Character>) {
        val content = characters.joinToString("\n") { serialize(it) }
        file.writeText(content)
    }

    fun loadCharacters(): MutableList<Character> {
        if (!file.exists()) return mutableListOf()
        return try {
            file.readLines()
                .filter { it.isNotBlank() }
                .map { deserialize(it) }
                .toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun serialize(c: Character): String {
        val weaponsStr = c.weapons.joinToString(";") { w ->
            "${w.name},${w.action},${w.range},${w.attacks},${w.power},${w.damage}"
        }
        return "${c.id}|${c.portraitPath}|${c.name}|${c.role}|${c.skill}|${c.action}|${c.movement}|${c.reaction}|${c.armor}|${c.hearts}|${c.abilities.replace("\n", "\\n")}|$weaponsStr"
    }

    private fun deserialize(s: String): Character {
        val parts = s.split("|")
        val weaponsStr = if (parts.size > 11) parts[11] else ""
        val weapons = if (weaponsStr.isBlank()) mutableListOf() else {
            weaponsStr.split(";").map { wStr ->
                val wParts = wStr.split(",")
                if (wParts.size == 6) {
                    Weapon(wParts[0], wParts[1].toInt(), wParts[2], wParts[3].toInt(), wParts[4].toInt(), wParts[5].toInt())
                } else {
                    // Fallback for old format
                    Weapon("", wParts[0].toInt(), wParts[1], wParts[2].toInt(), wParts[3].toInt(), wParts[4].toInt())
                }
            }.toMutableList()
        }
        return Character(
            id = UUID.fromString(parts[0]),
            portraitPath = parts[1],
            name = parts[2],
            role = parts[3],
            skill = parts[4],
            action = parts[5].toIntOrNull() ?: 0,
            movement = parts[6],
            reaction = parts[7].toIntOrNull() ?: 0,
            armor = parts[8].toIntOrNull() ?: 0,
            hearts = parts[9].toIntOrNull() ?: 0,
            abilities = parts[10].replace("\\n", "\n"),
            weapons = weapons
        )
    }
}
