package com.neronguyen.psychicmemory.core.network

import com.neronguyen.psychicmemory.core.model.UserMessage
import kotlinx.coroutines.flow.Flow


interface NetworkDataSource {

    suspend fun connectToSocket(url: String, token: String): Flow<String>

    suspend fun sendMessage(message: String)

    suspend fun disconnectSocket()

    suspend fun getChatHistory(url: String, token: String): List<UserMessage>
}
