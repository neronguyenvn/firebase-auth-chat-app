package com.neronguyen.psychicmemory.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserMessage(
    val userId: String,
    val username: String,
    val photoUrl: String,
    val content: String,
)
