package com.neronguyen.psychicmemory.core.firebase.util

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neronguyen.psychicmemory.core.model.User
import kotlinx.coroutines.tasks.await

val currentUser get() = Firebase.auth.currentUser!!.asExternalModel()
suspend fun getToken() = Firebase.auth.currentUser!!.getIdToken(true).await().token!!

private fun FirebaseUser.asExternalModel() = User(
    id = uid,
    name = displayName ?: "New User",
    email = email ?: "New Email",
    photoUrl = photoUrl.toString()
)