package com.neronguyen.psychicmemory.core.model

data class ChatMessage(
    val username: String,
    val photoUrl: String,
    val content: String,
    val sender: Sender
) {
    enum class Sender { CURRENT_USER, OTHER }
}