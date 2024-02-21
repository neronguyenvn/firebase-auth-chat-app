package com.neronguyen.psychicmemory.core.network.di

import com.neronguyen.psychicmemory.core.network.NetworkDataSource
import com.neronguyen.psychicmemory.core.network.ktor.KtorNetwork
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(CIO) {
            install(WebSockets)
        }
    }
    single<NetworkDataSource> { KtorNetwork(get()) }
}