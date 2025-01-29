package com.example.virtualwizard.data.model

data class PlayerData(
    val currentStoryNode: String = "start", // Player's current story progression
    val items: List<String> = emptyList(),  // Player's collected items
    val stats: PlayerStats = PlayerStats(), // Player's stats
    val crucialInfo: Map<String, String> = emptyMap() // Other important information
)

data class PlayerStats(
    val health: Int = 100,
    val mana: Int = 50,
    val strength: Int = 10,
    val intelligence: Int = 10,
)
