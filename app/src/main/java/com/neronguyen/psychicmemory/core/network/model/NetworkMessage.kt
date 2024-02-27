package com.neronguyen.psychicmemory.core.network.model

import com.neronguyen.psychicmemory.core.model.ChatMessage
import com.neronguyen.psychicmemory.core.model.ChatMessage.Sender.CURRENT_USER
import com.neronguyen.psychicmemory.core.model.ChatMessage.Sender.OTHER
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessage(
    val userId: String,
    val username: String,
    val photoUrl: String,
    val content: String,
)

fun NetworkMessage.asExternalModel(currentUserId: String) = ChatMessage(
    username = username,
    photoUrl = photoUrl,
    content = content,
    sender = if (userId == currentUserId) CURRENT_USER else OTHER
)