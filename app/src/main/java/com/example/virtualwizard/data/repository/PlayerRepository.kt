package com.example.virtualwizard.data.repository

import androidx.compose.runtime.mutableStateOf
import com.example.virtualwizard.data.model.PlayerData

object PlayerRepository {
    private var playerData = mutableStateOf(PlayerData())

    // Get current player data
    fun getPlayerData(): PlayerData = playerData.value

    // Update player data completely
    fun updatePlayerData(newData: PlayerData) {
        playerData.value = newData
    }

    // Update specific fields
    fun updateStoryNode(node: String) {
        playerData.value = playerData.value.copy(currentStoryNode = node)
    }

    fun addItem(item: String) {
        val updatedItems = playerData.value.items + item
        playerData.value = playerData.value.copy(items = updatedItems)
    }

    fun updateStats(health: Int? = null, mana: Int? = null, strength: Int? = null, intelligence: Int? = null) {
        val stats = playerData.value.stats
        val updatedStats = stats.copy(
            health = health ?: stats.health,
            mana = mana ?: stats.mana,
            strength = strength ?: stats.strength,
            intelligence = intelligence ?: stats.intelligence
        )
        playerData.value = playerData.value.copy(stats = updatedStats)
    }
}
