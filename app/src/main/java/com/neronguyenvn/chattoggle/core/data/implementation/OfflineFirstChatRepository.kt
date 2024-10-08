package com.neronguyenvn.chattoggle.core.data.implementation

import com.neronguyenvn.chattoggle.core.common.constant.Endpoint
import com.neronguyenvn.chattoggle.core.data.ChatRepository
import com.neronguyenvn.chattoggle.core.database.LocalDataSource
import com.neronguyenvn.chattoggle.core.database.model.MessageEntity
import com.neronguyenvn.chattoggle.core.database.model.asExternalModel
import com.neronguyenvn.chattoggle.core.firebase.util.currentUser
import com.neronguyenvn.chattoggle.core.firebase.util.getToken
import com.neronguyenvn.chattoggle.core.model.ChatMessage
import com.neronguyenvn.chattoggle.core.model.asEntity
import com.neronguyenvn.chattoggle.core.network.NetworkDataSource
import com.neronguyenvn.chattoggle.core.network.model.asEntity
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OfflineFirstChatRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ChatRepository {

    override suspend fun connectToSocket() {
        withContext(ioDispatcher) {
            networkDataSource.connectToSocket(
                url = Endpoint.CHAT_WEBSOCKET,
                token = getToken()
            ).collect { message ->
                localDataSource.insertMessage(message.asEntity())
            }
        }
    }

    override suspend fun sendMessage(message: String) {
        withContext(ioDispatcher) {
            networkDataSource.sendMessage(message)
            localDataSource.insertMessage(MessageEntity().apply {
                content = message
                timestamp = RealmInstant.now()
                senderInfo = currentUser.asEntity()
            })
        }
    }

    override suspend fun disconnectFromSocket() {
        withContext(ioDispatcher) {
            networkDataSource.disconnectSocket()
        }
    }

    override fun getChatHistoryStream(): Flow<List<ChatMessage>> {
        return localDataSource.observeAllMessages()
            .map { messages ->
                messages.map { message ->
                    message.asExternalModel(currentUser.id)
                }
            }
    }

    override suspend fun refreshChatHistory() {
        withContext(ioDispatcher) {
            val chatHistory = async {
                networkDataSource.getChatHistory(
                    url = Endpoint.CHAT_HISTORY,
                    token = getToken()
                )
            }
            localDataSource.deleteAllMessages()
            localDataSource.insertMessages(chatHistory.await().map { it.asEntity() })
        }
    }
}
