package com.example.smartlightapp.feature_smartLight.data.mqtt


object MqttConstants {
    const val MQTT_BROKER_URI = "tcp://broker.hivemq.com:1883" // public test broker
    const val CLIENT_ID = "SmartLightClient"
    const val TOPIC_COMMAND = "smartlight/command"
    const val TOPIC_STATE = "smartlight/state"
}