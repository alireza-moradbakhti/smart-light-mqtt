package com.example.smartlightapp.feature_smartLight.domain.repository

import com.example.smartlightapp.feature_smartLight.domain.model.LightState

interface MqttRepository {
    suspend fun toggleLight(isOn: Boolean)
    suspend fun setBrightness(brightness: Int)
    suspend fun getCurrentLightState(): LightState
    fun subscribeToLightState(onStateUpdated: (LightState) -> Unit)
}