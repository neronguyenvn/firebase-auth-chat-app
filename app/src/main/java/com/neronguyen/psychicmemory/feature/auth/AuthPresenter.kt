package com.neronguyen.psychicmemory.feature.auth

import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.neronguyen.psychicmemory.core.firebase.auth.GoogleAuthClient
import com.neronguyen.psychicmemory.feature.auth.AuthScreen.Event.SignInClicked
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.popUntil
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AuthPresenter(
    private val navigator: Navigator,
    private val googleAuthClient: GoogleAuthClient,
) : Presenter<AuthScreen.State> {

    @Composable
    override fun present(): AuthScreen.State {
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val launcher = rememberGoogleAuthLauncher(coroutineScope)

        return AuthScreen.State { event ->
            when (event) {
                SignInClicked -> coroutineScope.launch {
                    val intentSender = googleAuthClient.getSignInIntent()
                    launcher.launch(
                        IntentSenderRequest.Builder(intentSender ?: return@launch).build()
                    )
                }
            }
        }
    }

    @Composable
    private fun rememberGoogleAuthLauncher(
        coroutineScope: CoroutineScope = rememberCoroutineScope()
    ): ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                coroutineScope.launch {
                    googleAuthClient.signInWithIntent(intent = result.data ?: return@launch)
                    navigator.resetRoot(ChatRoomScreen)
                }
            }
        }
    }

    class Factory(private val googleAuthClient: GoogleAuthClient) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            return when (screen) {
                AuthScreen -> AuthPresenter(navigator, googleAuthClient)
                else -> null
            }
        }
    }
}
