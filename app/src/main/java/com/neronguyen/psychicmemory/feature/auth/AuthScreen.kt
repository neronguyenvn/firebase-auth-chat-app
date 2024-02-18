package com.neronguyen.psychicmemory.feature.auth

import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import com.neronguyen.psychicmemory.core.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AuthRoute(
    isAlreadySignedIn: Boolean,
    googleAuthClient: GoogleAuthClient = koinInject(),
    viewModel: AuthViewModel = koinInject(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authLauncher = googleAuthClient.rememberGoogleAuthLauncher()

    LaunchedEffect(Unit) {
        viewModel.updateAuthUser()
    }


    Box(Modifier.fillMaxSize().padding(16.dp), Alignment.Center) {
        if (!isAlreadySignedIn) {
            SignInButton {
                coroutineScope.launch {
                    val intentSender = googleAuthClient.getSignInIntent()
                    authLauncher.launch(
                        IntentSenderRequest.Builder(
                            intentSender ?: return@launch
                        ).build()
                    )
                }
            }
        } else {
            ProfileContent(userData = uiState!!.userData!!) {
                coroutineScope.launch {
                    googleAuthClient.signOut()
                }
            }
        }
    }
}

@Composable
fun SignInButton(onSignInClick: () -> Unit) {
    Button(onClick = onSignInClick) {
        Text("Sign In")
    }
}

@Composable
fun ProfileContent(userData: UserData, onSignOutClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = buildString {
            appendLine(userData.userId)
            appendLine(userData.username)
            appendLine(userData.profilePictureUrl)
            Log.d("AuthScreen", userData.token.orEmpty())
        })
        Button(onClick = onSignOutClick) {
            Text("Sign Out")
        }
    }
}