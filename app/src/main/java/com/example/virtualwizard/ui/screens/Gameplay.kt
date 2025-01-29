package com.example.virtualwizard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Gameplay(state: String, onNavigateToFighting: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Gameplay State: $state")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToFighting) {
                Text("Go to Fighting Screen")
            }
        }
    }
}
