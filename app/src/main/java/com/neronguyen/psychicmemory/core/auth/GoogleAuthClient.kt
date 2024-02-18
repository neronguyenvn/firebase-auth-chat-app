package com.neronguyen.psychicmemory.core.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neronguyen.psychicmemory.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.neronguyen.psychicmemory.core.model.UserData as DomainUserData

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    private val _currentUser = MutableStateFlow<DomainUserData?>(null)
    val currentUser = _currentUser.asStateFlow()

    suspend fun getSignInIntent(): IntentSender? {
        val result = oneTapClient.beginSignIn(buildSignInRequest()).await()
        return result?.pendingIntent?.intentSender
    }

    @Composable
    fun rememberGoogleAuthLauncher(
        coroutineScope: CoroutineScope = rememberCoroutineScope()
    ): ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == ComponentActivity.RESULT_OK) {
                    coroutineScope.launch {
                        signInWithIntent(intent = result.data ?: return@launch)
                    }
                }
            }
        )
    }

    suspend fun signOut() {
        oneTapClient.signOut().await()
        auth.signOut()
        updateCurrentUser()
    }

     suspend fun updateCurrentUser() {
        _currentUser.value = auth.currentUser?.run {
            DomainUserData(
                userId = uid,
                username = displayName,
                profilePictureUrl = photoUrl?.toString(),
                token = getIdToken(false).await().token.orEmpty()
            )
        }
    }

    private suspend fun signInWithIntent(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        auth.signInWithCredential(googleCredentials).await()
        updateCurrentUser()
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