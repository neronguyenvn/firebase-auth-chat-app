package com.neronguyen.psychicmemory.core.data

import com.neronguyen.psychicmemory.core.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun connectToSocket()

    suspend fun sendMessage(message: String)

    suspend fun disconnectFromSocket()

    fun getChatHistoryStream(): Flow<List<ChatMessage>>

    suspend fun refreshChatHistory()
}
