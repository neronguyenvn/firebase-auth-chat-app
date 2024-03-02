package com.neronguyen.psychicmemory.core.database.realm

import com.neronguyen.psychicmemory.core.database.LocalDataSource
import com.neronguyen.psychicmemory.core.database.model.MessageEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmDatabase(private val realm: Realm) : LocalDataSource {

    override fun observeAllMessages(): Flow<List<MessageEntity>> {
        return realm.query<MessageEntity>()
            .asFlow()
            .map { results ->
                results.list.toList()
            }
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        realm.write {
            messages.forEach { message -> copyToRealm(message) }
        }
    }

    override suspend fun deleteAllMessages() {
        realm.write {
            val chatHistory = query<MessageEntity>()
            delete(chatHistory)
        }
    }
}
