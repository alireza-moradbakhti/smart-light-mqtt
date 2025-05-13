package com.example.smartlightapp.feature_smartLight.data.mqtt


object MqttConstants {
    const val HOST = "broker.hivemq.com" // or your own host
    const val PORT = 1883
    const val CLIENT_ID = "SmartLightAppClient"
    const val TOPIC_COMMAND = "smartlight/command"
    const val TOPIC_STATE = "smartlight/state"
}