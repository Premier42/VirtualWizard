package com.example.virtualwizard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FightingScreen(state: String, onVictory: () -> Unit, onDefeat: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Fighting State: $state")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onVictory) {
                Text("Victory")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onDefeat) {
                Text("Defeat")
            }
        }
    }
}
