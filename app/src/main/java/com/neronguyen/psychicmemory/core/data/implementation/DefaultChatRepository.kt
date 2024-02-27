package com.neronguyen.psychicmemory.core.data.implementation

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neronguyen.psychicmemory.core.data.ChatRepository
import com.neronguyen.psychicmemory.core.model.UserMessage
import com.neronguyen.psychicmemory.core.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DefaultChatRepository(
    private val networkDataSource: NetworkDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ChatRepository {

    private suspend fun getToken() = Firebase.auth.currentUser!!.getIdToken(true).await().token!!

    override suspend fun connectToSocket(): Flow<String> {
        return withContext(ioDispatcher) {
            networkDataSource.connectToSocket(
                url = "ws://192.168.1.5:8080/chat",
                token = getToken()
            )
        }
    }

    override suspend fun sendMessage(message: String) {
        withContext(ioDispatcher) {
            networkDataSource.sendMessage(message)
        }
    }

    override suspend fun disconnect() {
        withContext(ioDispatcher) {
            networkDataSource.disconnectSocket()
        }
    }

    override suspend fun getChatHistory(): List<UserMessage> {
        return withContext(ioDispatcher) {
            networkDataSource.getChatHistory(
                url = "http://192.168.1.5:8080/chatHistory",
                token = getToken()
            )
        }
    }
}
