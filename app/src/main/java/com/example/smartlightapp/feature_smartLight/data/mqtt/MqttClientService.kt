package com.example.smartlightapp.feature_smartLight.data.mqtt

interface MqttClientService {
    fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit)
    fun publishCommand(message: String)
    fun subscribeToState(onMessageReceived: (String) -> Unit)
    fun disconnect()
}