package com.example.virtualwizard.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualwizard.data.model.PlayerData
import com.example.virtualwizard.data.repository.PlayerRepository
import com.example.virtualwizard.data.storage.StorageManager
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    // Expose the player data
    val playerData = PlayerRepository.getPlayerData()

    // Load saved data
    fun loadPlayerData() {
        viewModelScope.launch {
            val loadedData = StorageManager.loadPlayerData(context)
            PlayerRepository.updatePlayerData(loadedData)
        }
    }

    // Save current data
    fun savePlayerData() {
        viewModelScope.launch {
            StorageManager.savePlayerData(context, playerData)
        }
    }
}
