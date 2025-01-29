package com.example.virtualwizard.data.storage

import android.content.Context
import com.example.virtualwizard.data.model.PlayerData
import com.google.gson.Gson

object StorageManager {
    private const val PREFS_NAME = "player_prefs"
    private const val KEY_PLAYER_DATA = "player_data"

    fun savePlayerData(context: Context, playerData: PlayerData) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(playerData)
        editor.putString(KEY_PLAYER_DATA, json)
        editor.apply()
    }

    fun loadPlayerData(context: Context): PlayerData {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_PLAYER_DATA, null)
        return if (json != null) {
            Gson().fromJson(json, PlayerData::class.java)
        } else {
            PlayerData() // Default data
        }
    }
}
