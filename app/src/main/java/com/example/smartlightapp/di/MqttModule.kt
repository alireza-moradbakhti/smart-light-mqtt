package com.example.smartlightapp.di

import com.example.smartlightapp.feature_smartLight.data.mqtt.MqttClientService
import com.example.smartlightapp.feature_smartLight.data.mqtt.MqttClientServiceImpl
import com.example.smartlightapp.feature_smartLight.data.repository.MqttRepositoryImpl
import com.example.smartlightapp.feature_smartLight.domain.repository.MqttRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MqttModule {

    @Provides
    @Singleton
    fun provideMqttClientService(): MqttClientService = MqttClientServiceImpl()

    @Provides
    @Singleton
    fun provideMqttRepository(
        mqttClientService: MqttClientService
    ): MqttRepository = MqttRepositoryImpl(mqttClientService)

}