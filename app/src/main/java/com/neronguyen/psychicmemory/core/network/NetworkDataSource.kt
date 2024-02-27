package com.neronguyen.psychicmemory.core.network

import com.neronguyen.psychicmemory.core.network.model.NetworkMessage
import kotlinx.coroutines.flow.Flow


interface NetworkDataSource {

    suspend fun connectToSocket(url: String, token: String): Flow<NetworkMessage>

    suspend fun sendMessage(message: String)

    suspend fun disconnectSocket()

    suspend fun getChatHistory(url: String, token: String): List<NetworkMessage>
}
