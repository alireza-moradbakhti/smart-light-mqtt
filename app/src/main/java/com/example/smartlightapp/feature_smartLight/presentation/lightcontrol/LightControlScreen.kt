package com.example.smartlightapp.feature_smartLight.presentation.lightcontrol

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LightControlScreen(
    viewModel: LightControlViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = if (uiState.isConnected) "Connected ✅" else "Connecting...",
            fontSize = 20.sp
        )

        Text(text = "Light is ${if (uiState.isOn) "ON" else "OFF"}")

        Button(onClick = {
            viewModel.toggleLight(!uiState.isOn)
        }) {
            Text(if (uiState.isOn) "Turn OFF" else "Turn ON")
        }

        Text(text = "Brightness: ${uiState.brightness}%")

        Slider(
            value = uiState.brightness.toFloat(),
            onValueChange = { viewModel.changeBrightness(it.toInt()) },
            valueRange = 0f..100f
        )

        uiState.error?.let { errorMsg ->
            Text(
                text = "Error: $errorMsg (tap to dismiss)",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.clearError() }
                    .padding(8.dp)
            )
        }
    }
}