package com.neronguyen.psychicmemory.core.data

import com.neronguyen.psychicmemory.core.model.UserMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun connectToSocket(): Flow<String>

    suspend fun sendMessage(message: String)

    suspend fun disconnect()

    suspend fun getChatHistory(): List<UserMessage>
}
