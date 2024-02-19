package com.neronguyen.psychicmemory.core.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neronguyen.psychicmemory.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import com.neronguyen.psychicmemory.core.domain.model.User as DomainUser

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    private val _currentUser = MutableStateFlow<DomainUser?>(null)
    val currentUser = _currentUser.asStateFlow()

    suspend fun getSignInIntent(): IntentSender? {
        val result = oneTapClient.beginSignIn(buildSignInRequest()).await()
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        auth.signInWithCredential(googleCredentials).await()
        updateCurrentUser()
    }

    suspend fun signOut() {
        oneTapClient.signOut().await()
        auth.signOut()
        updateCurrentUser()
    }

    private suspend fun updateCurrentUser() {
        _currentUser.value = auth.currentUser?.run {
            DomainUser(
                userId = uid,
                username = displayName,
                profilePictureUrl = photoUrl?.toString(),
                token = getIdToken(true).await().token.orEmpty()
            )
        }
    }

    private fun buildSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
}