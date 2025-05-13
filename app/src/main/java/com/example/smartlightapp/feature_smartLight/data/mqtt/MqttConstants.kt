package com.example.smartlightapp.feature_smartLight.data.mqtt


object MqttConstants {
    const val MQTT_BROKER_URI = "tcp://broker.hivemq.com:1883"
    const val CLIENT_ID = "AndroidSmartLightClient" // should be unique per client
    const val TOPIC_COMMAND = "test/smartlight/command"
    const val TOPIC_STATE = "test/smartlight/state"
}