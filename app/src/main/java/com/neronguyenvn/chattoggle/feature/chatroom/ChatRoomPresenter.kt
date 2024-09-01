package com.neronguyenvn.chattoggle.feature.chatroom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.neronguyenvn.chattoggle.core.common.util.observeLifecycle
import com.neronguyenvn.chattoggle.core.data.ChatRepository
import com.neronguyenvn.chattoggle.core.firebase.auth.GoogleAuthClient
import com.neronguyenvn.chattoggle.core.firebase.util.currentUser
import com.neronguyenvn.chattoggle.core.model.ChatMessage
import com.neronguyenvn.chattoggle.feature.auth.AuthScreen
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomScreen.Event.ConnectSocket
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomScreen.Event.InputMessage
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomScreen.Event.SendMessage
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomScreen.Event.SignOut
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration.Companion.hours

class ChatRoomPresenter(
    private val chatRepository: ChatRepository,
    private val googleAuthClient: GoogleAuthClient,
    private val navigator: Navigator
) : Presenter<ChatRoomScreen.State> {

    @Composable
    override fun present(): ChatRoomScreen.State {
        val coroutineScope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current

        lifecycleOwner.observeLifecycle(
            doOnStart = { coroutineScope.launch { chatRepository.connectToSocket() } },
            doOnStop = { coroutineScope.launch { chatRepository.disconnectFromSocket() } },
        )

        var inputMessage by rememberSaveable { mutableStateOf("") }
        val chatHistory by chatRepository.getChatHistoryStream()
            .map { chatHistory ->
                chatHistory
                    .mapIndexed { index, message ->
                        shouldShowInfo(index, message, chatHistory)
                    }
                    .mapIndexed { index, message ->
                        shouldShowTimestamp(index, message, chatHistory)
                    }
                    .reversed()
            }
            .collectAsState(initial = emptyList())

        return ChatRoomScreen.State(
            currentUser = currentUser,
            inputMessage = inputMessage,
            chatHistory = chatHistory,
        ) { event ->
            when (event) {

                ConnectSocket -> coroutineScope.launch {
                    launch { Firebase.messaging.subscribeToTopic("chat").await() }
                    launch { chatRepository.refreshChatHistory() }
                }

                is InputMessage -> inputMessage = event.value

                is SendMessage -> coroutineScope.launch {
                    inputMessage = ""
                    chatRepository.sendMessage(event.message)
                }

                is SignOut -> coroutineScope.launch {
                    launch {
                        launch { googleAuthClient.signOut() }
                        launch { chatRepository.disconnectFromSocket() }
                    }.join()
                    navigator.resetRoot(AuthScreen)
                }
            }
        }
    }

    private fun shouldShowInfo(
        index: Int,
        message: ChatMessage,
        chatHistory: List<ChatMessage>
    ): ChatMessage {

        if (message is ChatMessage.CurrentUserMessage) {
            return message
        }

        message as ChatMessage.OtherMessage
        if (index == 0) {
            return message.copy(shouldShowInfo = true)
        }

        if (message.senderInfo.uid != chatHistory[index - 1].senderInfo.uid) {
            return message.copy(shouldShowInfo = true)
        }

        return message.copy(shouldShowInfo = false)
    }

    private fun shouldShowTimestamp(
        index: Int,
        message: ChatMessage,
        chatHistory: List<ChatMessage>
    ): ChatMessage {

        if (index == 0) {
            return message.copyNewShouldShowTimestamp(true)
        }

        if ((message.timestamp - chatHistory[index - 1].timestamp) > 1.hours) {
            return message.copyNewShouldShowTimestamp(true)
        }

        return message.copyNewShouldShowTimestamp(false)
    }

    class Factory(
        private val chatRepository: ChatRepository,
        private val googleAuthClient: GoogleAuthClient
    ) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            return when (screen) {
                ChatRoomScreen -> ChatRoomPresenter(chatRepository, googleAuthClient, navigator)
                else -> null
            }
        }
    }
}
