package com.neronguyen.psychicmemory.core.network.di

import com.neronguyen.psychicmemory.core.network.NetworkDataSource
import com.neronguyen.psychicmemory.core.network.ktor.KtorNetwork
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single { Json { ignoreUnknownKeys = true } }
    single {
        HttpClient(CIO) {
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(get<Json>())
            }
            install(ContentNegotiation) {
                json(get())
            }
        }
    }
    single<NetworkDataSource> { KtorNetwork(get()) }
}
