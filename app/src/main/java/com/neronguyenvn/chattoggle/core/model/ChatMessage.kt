package com.neronguyenvn.chattoggle.core.model

import com.neronguyenvn.chattoggle.core.database.model.MessageEntity
import com.neronguyenvn.chattoggle.core.database.model.SenderInfoEntity
import io.realm.kotlin.types.RealmInstant
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed class ChatMessage {

    abstract val content: String
    abstract val timestamp: Instant
    abstract val senderInfo: SenderInfo
    abstract val shouldShowTimestamp: Boolean

    data class OtherMessage(
        override val content: String,
        override val timestamp: Instant,
        override val senderInfo: SenderInfo,
        val shouldShowInfo: Boolean = false,
        override val shouldShowTimestamp: Boolean = false
    ) : ChatMessage()


    data class CurrentUserMessage(
        override val content: String,
        override val timestamp: Instant,
        override val senderInfo: SenderInfo,
        override val shouldShowTimestamp: Boolean = false
    ) : ChatMessage()

    fun copyNewShouldShowTimestamp(should: Boolean): ChatMessage {
        return when (this) {
            is OtherMessage -> this.copy(shouldShowTimestamp = should)
            is CurrentUserMessage -> this.copy(shouldShowTimestamp = should)
        }
    }
}

@Serializable
data class SenderInfo(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
)


fun ChatMessage.asEntity() = MessageEntity().apply {
    content = this@asEntity.content
    timestamp = RealmInstant.from(this@asEntity.timestamp.epochSeconds, 0)
    senderInfo = this@asEntity.senderInfo.asEntity()
}

fun SenderInfo.asEntity() = SenderInfoEntity().apply {
    uid = this@asEntity.uid
    name = this@asEntity.name
    email = this@asEntity.email
    photoUrl = this@asEntity.photoUrl
}
