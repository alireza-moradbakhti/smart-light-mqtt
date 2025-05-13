package com.example.smartlightapp.feature_smartLight.presentation.lightcontrol

data class LightControlUiState(
    val isOn: Boolean = false,
    val brightness: Int = 0,
    val isConnected: Boolean = false,
    val error: String? = null
)
