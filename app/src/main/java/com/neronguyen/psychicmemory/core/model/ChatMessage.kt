package com.neronguyen.psychicmemory.core.model

import com.neronguyen.psychicmemory.core.network.model.SenderInfo
import kotlinx.datetime.Instant

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
