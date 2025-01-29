package com.example.virtualwizard.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.virtualwizard.R
import com.example.virtualwizard.utils.fetchRandomQuote
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(navController: NavController, navigateToMenu: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    // Animation for the "Loading" text
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Loading states
    var quote by remember { mutableStateOf("Fetching quote...") }
    var isLoadingComplete by remember { mutableStateOf(false) }

    // Load tasks
    LaunchedEffect(Unit) {
        coroutineScope.launch { quote = fetchRandomQuote() } // Fetch a quote
        coroutineScope.launch {
            delay(2500) // Simulate loading time
            isLoadingComplete = true
        }
    }

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = isLoadingComplete) { navigateToMenu() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder_bg),
            contentDescription = "Loading Screen Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (!isLoadingComplete) {
                Text(
                    text = "Loading...",
                    fontSize = 24.sp,
                    color = Color.White.copy(alpha = alpha),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = quote,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            if (isLoadingComplete) {
                Text(
                    text = "Click anywhere to continue",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
