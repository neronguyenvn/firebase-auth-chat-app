package com.neronguyenvn.chattoggle.feature.auth

import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.neronguyenvn.chattoggle.core.firebase.auth.GoogleAuthClient
import com.neronguyenvn.chattoggle.feature.auth.AuthScreen.Event.SignInClicked
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AuthPresenter(
    private val navigator: Navigator,
    private val googleAuthClient: GoogleAuthClient,
) : Presenter<AuthScreen.State> {

    private var isLoading by mutableStateOf(false)

    @Composable
    override fun present(): AuthScreen.State {
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val launcher = rememberGoogleAuthLauncher(coroutineScope)

        return AuthScreen.State(isLoading) { event ->
            when (event) {
                SignInClicked -> coroutineScope.launch {
                    isLoading = true
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
                    isLoading = false
                }
            } else {
                isLoading = false
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
