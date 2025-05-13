# 💡 Smart Light Control App

An Android app built with Kotlin and Jetpack Compose to simulate real-time control of a smart light bulb using MQTT. This take-home project demonstrates modern Android development with clean architecture, MVVM, Hilt, and real-time communication using the hive MQTT client.

## ✨ Features

- ✅ Turn smart light ON and OFF
- ✅ Adjust brightness from 0% to 100%
- ✅ View real-time light state updates
- ✅ Built with clean, maintainable architecture

## 🔧 Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Hilt
- Retrofit
- Coroutines + StateFlow
- Room (optional)
- Hive MQTT Client

## 🚀 Getting Started

```bash
git clone https://github.com/alireza-moradbakhti/smart-light-mqtt.git
Open in Android Studio and run. Simulate MQTT responses manually using tools like MQTT Explorer.

🔄 MQTT Topics
Publish to smartlight/command

Subscribe to smartlight/state

Example payload:
{"isOn": true, "brightness": 80}


🙋‍♂️ Author
Made with ❤️ by Alireza Moradbakhti
