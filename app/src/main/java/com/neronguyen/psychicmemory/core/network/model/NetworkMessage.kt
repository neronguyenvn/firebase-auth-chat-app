package com.neronguyen.psychicmemory.core.network.model

import com.neronguyen.psychicmemory.core.model.ChatMessage
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessage(
    val content: String,
    val timestamp: Instant,
    val senderInfo: SenderInfo
)

@Serializable
data class SenderInfo(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
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