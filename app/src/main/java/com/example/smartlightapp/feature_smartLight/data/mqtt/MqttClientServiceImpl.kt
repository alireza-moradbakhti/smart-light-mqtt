package com.example.smartlightapp.feature_smartLight.data.mqtt

import android.util.Log
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttClientServiceImpl @Inject constructor() : MqttClientService {

    private val client: Mqtt3AsyncClient = MqttClient.builder()
        .useMqttVersion3()
        .identifier(MqttConstants.CLIENT_ID)
        .serverHost(MqttConstants.HOST)
        .serverPort(MqttConstants.PORT) // default 1883
        .automaticReconnectWithDefaultConfig()
        .buildAsync()

    override fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit) {
        client.connect()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Log.e("MQTT", "Connection failed", throwable)
                    onError(throwable)
                } else {
                    Log.i("MQTT", "Connected")
                    onConnected()
                }
            }
    }

    override fun publishCommand(message: String) {
        client.publishWith()
            .topic(MqttConstants.TOPIC_COMMAND)
            .payload(message.toByteArray(StandardCharsets.UTF_8))
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Log.e("MQTT", "Publish failed", throwable)
                }
            }
    }

    override fun subscribeToState(onMessageReceived: (String) -> Unit) {
        client.subscribeWith()
            .topicFilter(MqttConstants.TOPIC_STATE)
            .callback { publish: Mqtt3Publish ->
                val message = publish.payload.map {
                    StandardCharsets.UTF_8.decode(it).toString()
                }.orElse("")
                onMessageReceived(message)
            }
            .send()
    }

    override fun disconnect() {
        try {
            client.disconnect() // HiveMQ client's disconnect is usually synchronous
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
