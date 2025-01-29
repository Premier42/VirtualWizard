package com.example.virtualwizard.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedInputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// Existing fetchRandomQuote function
suspend fun fetchRandomQuote(): String {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("https://zenquotes.io/api/random")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedInputStream(connection.inputStream).bufferedReader().use { reader ->
                    val response = reader.readText()
                    val jsonArray = JSONArray(response)
                    val json = jsonArray.getJSONObject(0)
                    val content = json.getString("q")
                    val author = json.getString("a")
                    "\"$content\" - $author"
                }
            } else {
                "Unable to fetch a quote. Please try again."
            }
        } catch (e: Exception) {
            "Failed to fetch quote: ${e.localizedMessage}"
        }
    }
}

// New fightSequence function for tracking movement
fun fightSequence(
    context: Context,
    isRecording: MutableState<Boolean>,
    path: MutableList<Pair<Float, Float>>, // Changed to MutableList
    onRecordToggle: () -> Unit
) {
    // Initialize the SensorManager
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    // Sensor event listener for capturing phone movement
    val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null && isRecording.value) {
                // Handle accelerometer data
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val x = event.values[0]
                    val y = event.values[1]

                    // Record the position as (x, y) in the path list
                    path.add(Pair(x, y))
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    // Register listeners for accelerometer and gyroscope
    sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
    sensorManager.registerListener(sensorListener, gyroscope, SensorManager.SENSOR_DELAY_UI)

    // Composable function to display the path and toggle recording
    @Composable
    fun DrawingApp() {
        Column(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw the path if it exists
                if (path.isNotEmpty()) {
                    for (i in 1 until path.size) {
                        val start = path[i - 1]
                        val end = path[i]

                        // Draw line from start to end position
                        drawLine(
                            start = Offset(start.first, start.second),
                            end = Offset(end.first, end.second),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                    }
                }
            }

            Button(onClick = onRecordToggle, modifier = Modifier.padding(16.dp)) {
                Text(if (isRecording.value) "Stop Recording" else "Start Recording")
            }
        }
    }

    // Toggle recording on button press
    isRecording.value = !isRecording.value
    if (!isRecording.value) {
        // Clear the path when recording stops
        path.clear()
    }

    // Remember to unregister sensor listeners when the recording stops
    if (!isRecording.value) {
        sensorManager.unregisterListener(sensorListener)
    }
}
