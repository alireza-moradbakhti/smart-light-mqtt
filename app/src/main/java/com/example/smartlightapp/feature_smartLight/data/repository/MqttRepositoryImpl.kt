package com.example.smartlightapp.feature_smartLight.data.repository

import com.example.smartlightapp.feature_smartLight.data.mqtt.MqttClientService
import com.example.smartlightapp.feature_smartLight.domain.model.LightState
import com.example.smartlightapp.feature_smartLight.domain.repository.MqttRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttRepositoryImpl @Inject constructor(
    private val mqttClientService: MqttClientService
) : MqttRepository {

    private var currentLightState = LightState(isOn = false, brightness = 0)

    override suspend fun toggleLight(isOn: Boolean) {
        val message = if (isOn) "ON" else "OFF"
        mqttClientService.publishCommand(message)
    }

    override suspend fun setBrightness(brightness: Int) {
        val message = "BRIGHTNESS:$brightness"
        mqttClientService.publishCommand(message)
    }

    override suspend fun getCurrentLightState(): LightState {
        return currentLightState
    }

    override fun subscribeToLightState(onStateUpdated: (LightState) -> Unit) {
        mqttClientService.subscribeToState { message ->
            val newState = parseLightState(message)
            currentLightState = newState
            onStateUpdated(newState)
        }
    }

    private fun parseLightState(message: String): LightState {
        return when {
            message == "ON" -> LightState(true, 100)
            message == "OFF" -> LightState(false, 0)
            message.startsWith("BRIGHTNESS:") -> {
                val brightness = message.split(":")[1].toIntOrNull() ?: 0
                LightState(true, brightness)
            }
            else -> LightState(false, 0)
        }
    }
}