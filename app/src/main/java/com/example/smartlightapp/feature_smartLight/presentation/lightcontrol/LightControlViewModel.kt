package com.example.smartlightapp.feature_smartLight.presentation.lightcontrol

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlightapp.feature_smartLight.domain.repository.MqttRepository
import com.example.smartlightapp.feature_smartLight.domain.usecase.SetBrightnessUseCase
import com.example.smartlightapp.feature_smartLight.domain.usecase.ToggleLightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LightControlViewModel @Inject constructor(
    private val toggleLightUseCase: ToggleLightUseCase,
    private val setBrightnessUseCase: SetBrightnessUseCase,
    private val mqttRepository: MqttRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LightControlUiState())
    val uiState: StateFlow<LightControlUiState> = _uiState.asStateFlow()

    init {
        connectToBroker()
    }

    private fun connectToBroker() {

        Log.d("MQTT", "Connecting to broker...")

        mqttRepository.connect(
            onConnected = {
                Log.d("MQTT", "Connected successfully")
                _uiState.update {
                    it.copy(isConnected = true)
                }
                mqttRepository.subscribeToLightState { lightState ->
                    Log.d("MQTT", "Message received: $lightState")
                    _uiState.update {
                        it.copy(
                            isOn = lightState.isOn,
                            brightness = lightState.brightness,
                        )
                    }
                }
            },
            onError = { error ->
                Log.e("MQTT", "Connection failed: ${error.message}")
                _uiState.update {
                    it.copy(error = error.message)
                }
            }
        )
    }

    fun toggleLight(isOn: Boolean) {
        viewModelScope.launch {
            try {
                toggleLightUseCase.execute(isOn)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun changeBrightness(brightness: Int) {
        viewModelScope.launch {
            try {
                setBrightnessUseCase.execute(brightness)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    override fun onCleared() {
        super.onCleared()
        mqttRepository.disconnect()
    }
}