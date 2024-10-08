package com.neronguyenvn.chattoggle.core.network.model

import com.neronguyenvn.chattoggle.core.database.model.MessageEntity
import com.neronguyenvn.chattoggle.core.model.ChatMessage
import com.neronguyenvn.chattoggle.core.model.SenderInfo
import com.neronguyenvn.chattoggle.core.model.asEntity
import io.realm.kotlin.types.RealmInstant
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessage(
    val content: String,
    val timestamp: Instant,
    val senderInfo: SenderInfo
)

fun NetworkMessage.asExternalModel(currentUserId: String): ChatMessage {
    return if (currentUserId == senderInfo.uid) ChatMessage.CurrentUserMessage(
        content = content,
        senderInfo = senderInfo,
        timestamp = timestamp
    ) else ChatMessage.OtherMessage(
        content = content,
        senderInfo = senderInfo,
        timestamp = timestamp
    )
}

fun NetworkMessage.asEntity() = MessageEntity().apply {
    content = this@asEntity.content
    timestamp = RealmInstant.from(this@asEntity.timestamp.epochSeconds, 0)
    senderInfo = this@asEntity.senderInfo.asEntity()
}
