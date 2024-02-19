package com.neronguyen.psychicmemory.core.domain.model
data class User(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
    val token: String?
)
