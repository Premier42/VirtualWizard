package com.example.virtualwizard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virtualwizard.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainMenu(
    onResumeGame: () -> Unit,
    onStartNewGame: () -> Unit,
    onOptions: () -> Unit,
    onExit: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) } // Dialog visibility
    var hasSavedGame by remember { mutableStateOf(false) } // State for saved game

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.placeholder_bg),
            contentDescription = "Main Menu Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Main Menu Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title
            Text(
                text = "Virtual Wizard",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 48.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Push buttons to bottom half

            // Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        // Check for saved game when Play Game is pressed
                        checkForSavedGame { exists ->
                            hasSavedGame = exists
                            if (exists) {
                                onResumeGame() // Resume game
                            } else {
                                onStartNewGame() // Start new game
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Play Game", fontSize = 18.sp)
                }

                Button(
                    onClick = { showDialog = true }, // Show confirmation dialog
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Start Anew", fontSize = 18.sp)
                }

                Button(
                    onClick = { onOptions() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Options", fontSize = 18.sp)
                }

                Button(
                    onClick = { onExit() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Exit", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Confirmation Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Confirm Action",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                text = {
                    Text(
                        text = "Are you sure? Starting anew will overwrite your previous data.",
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        onStartNewGame() // Overwrite and start new game
                    }) {
                        Text(text = "Proceed", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "Cancel")
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                containerColor = Color.DarkGray
            )
        }
    }
}

// Function to check if a saved game exists
fun checkForSavedGame(onResult: (Boolean) -> Unit) {
    // Simulated async check (replace with Firebase logic)
    onResult(false) // Example: No saved game found
}
