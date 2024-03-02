package com.neronguyen.psychicmemory.core.database

import com.neronguyen.psychicmemory.core.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun observeAllMessages(): Flow<List<MessageEntity>>

    suspend fun insertMessages(messages: List<MessageEntity>)

    suspend fun insertMessage(message: MessageEntity)

    suspend fun deleteAllMessages()
}
