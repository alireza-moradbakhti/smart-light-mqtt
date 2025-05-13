package com.example.smartlightapp.feature_smartLight.domain.usecase

import com.example.smartlightapp.feature_smartLight.domain.repository.MqttRepository
import javax.inject.Inject

class SetBrightnessUseCase @Inject constructor(
    private val mqttRepository: MqttRepository
) {
    suspend fun execute(brightness: Int) {
        mqttRepository.setBrightness(brightness)
    }
}