package com.neronguyen.psychicmemory.core.network.ktor

import com.neronguyen.psychicmemory.core.model.UserMessage
import com.neronguyen.psychicmemory.core.network.NetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.converter
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.serialization.deserialize
import io.ktor.websocket.close
import io.ktor.websocket.send
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow

class KtorNetwork(private val httpClient: HttpClient) : NetworkDataSource {

    private var webSocket: DefaultClientWebSocketSession? = null

    override suspend fun connectToSocket(url: String, token: String): Flow<UserMessage> {
        webSocket = httpClient.webSocketSession {
            url(url).apply {
                header(
                    "Authorization",
                    "Bearer $token"
                )
            }
        }
        return webSocket?.incoming?.receiveAsFlow()
            ?.map { webSocket?.converter!!.deserialize<UserMessage>(it) }
            ?: emptyFlow()
    }

    override suspend fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    override suspend fun disconnectSocket() {
        webSocket?.close()
    }

    override suspend fun getChatHistory(url: String, token: String): List<UserMessage> {
        return httpClient.get(url) {
            header(
                "Authorization",
                "Bearer $token"
            )
        }.body()
    }
}
