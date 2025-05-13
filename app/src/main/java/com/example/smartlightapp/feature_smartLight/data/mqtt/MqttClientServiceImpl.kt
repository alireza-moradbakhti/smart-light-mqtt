package com.example.smartlightapp.feature_smartLight.data.mqtt

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttClientServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MqttClientService {

    private val mqttClient: MqttAndroidClient = MqttAndroidClient(
        context,
        MqttConstants.MQTT_BROKER_URI,
        MqttConstants.CLIENT_ID
    )

    override fun connect(onConnected: () -> Unit, onError: (Throwable) -> Unit) {
        val options = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
        }

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                onConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                onError(exception ?: Exception("MQTT connection failed"))
            }
        })
    }

    override fun publishCommand(message: String) {
        val mqttMessage = MqttMessage().apply {
            payload = message.toByteArray()
        }
        mqttClient.publish(MqttConstants.TOPIC_COMMAND, mqttMessage)
    }

    override fun subscribeToState(onMessageReceived: (String) -> Unit) {
        mqttClient.subscribe(MqttConstants.TOPIC_STATE, 0)
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {}

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (topic == MqttConstants.TOPIC_STATE) {
                    onMessageReceived(message.toString())
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        })
    }

    override fun disconnect() {
        mqttClient.disconnect()
    }
}