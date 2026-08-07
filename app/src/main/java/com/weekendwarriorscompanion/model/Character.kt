package com.weekendwarriorscompanion.model

import java.util.UUID

data class Character(
    val id: UUID = UUID.randomUUID(),
    var portraitPath: String = "",
    var name: String = "",
    var role: String = "",
    var skill: String = "",
    var action: Int = 0,
    var movement: String = "",
    var reaction: Int = 0,
    var armor: Int = 0,
    var hearts: Int = 0,
    var weapons: MutableList<Weapon> = mutableListOf(Weapon(), Weapon()),
    var abilities: String = ""
)
