package com.neronguyenvn.chattoggle.core.data

import com.neronguyenvn.chattoggle.core.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun connectToSocket()

    suspend fun sendMessage(message: String)

    suspend fun disconnectFromSocket()

    fun getChatHistoryStream(): Flow<List<ChatMessage>>

    suspend fun refreshChatHistory()
}
