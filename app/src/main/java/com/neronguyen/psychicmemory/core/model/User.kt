package com.neronguyen.psychicmemory.core.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String
)

fun FirebaseUser.toBusinessModel() = User(
    id = uid,
    name = displayName ?: "New User",
    email = email ?: "New Email",
    photoUrl = photoUrl.toString()
)

