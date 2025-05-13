package com.example.smartlightapp.feature_smartLight.data.mqtt

import android.util.Log
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttClientServiceImpl @Inject constructor() : MqttClientService {

    private var isConnected = false

    val client: Mqtt5AsyncClient = MqttClient.builder()
        .identifier(MqttConstants.CLIENT_ID)
        .serverHost("broker.hivemq.com")
        .serverPort(1883)
        .useMqttVersion5()
        .buildAsync()

    override fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit) {
        client.connectWith()
            .cleanStart(true)
            .send()
            .whenComplete { ack, throwable ->
                if (throwable != null) {
                    onError(throwable)
                } else {
                    isConnected = true
                    onConnected()
                }
            }
    }

    override fun publishCommand(message: String) {
        if (!isConnected) {
            Log.e("MQTT", "Client not connected. Cannot publish.")
            return
        }

        client.publishWith()
            .topic(MqttConstants.TOPIC_COMMAND)
            .payload(message.toByteArray(StandardCharsets.UTF_8))
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    Log.e("MQTT", "Publish failed", throwable)
                } else {
                    Log.d("MQTT", "Command published: $message")
                }
            }
    }

    override fun subscribeToState(onMessageReceived: (String) -> Unit) {
        client.subscribeWith()
            .topicFilter(MqttConstants.TOPIC_STATE)
            .callback { publish ->
                val payload = publish.payload
                    .map { buffer ->
                        val bytes = ByteArray(buffer.remaining())
                        buffer.get(bytes)
                        String(bytes, Charsets.UTF_8)
                    }
                    .orElse("")
                onMessageReceived(payload)
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
