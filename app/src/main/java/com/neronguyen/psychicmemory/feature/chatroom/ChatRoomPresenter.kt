package com.neronguyen.psychicmemory.feature.chatroom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.neronguyen.psychicmemory.core.data.ChatRepository
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.ConnectSocket
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.InputMessage
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.SendMessage
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatRoomPresenter(
    private val chatRepository: ChatRepository
) : Presenter<ChatRoomScreen.State> {

    @Composable
    override fun present(): ChatRoomScreen.State {
        val coroutineScope = rememberCoroutineScope()

        val messages = remember { mutableStateListOf<String>() }
        var inputMessage by remember { mutableStateOf("") }

        return ChatRoomScreen.State(
            messages = messages,
            inputMessage = inputMessage
        ) { event ->
            when (event) {
                ConnectSocket -> coroutineScope.launch {
                    launch {
                        Firebase.messaging.subscribeToTopic("chat").await()
                    }
                    val chatHistory = chatRepository.getChatHistory()
                    messages.addAll(0, chatHistory.map { "[${it.username}]: ${it.content}" })
                    chatRepository.connectToSocket()
                        .onEach { message -> messages.add(0, message) }
                        .launchIn(coroutineScope)
                }

                is InputMessage -> inputMessage = event.value

                is SendMessage -> coroutineScope.launch {
                    inputMessage = ""
                    chatRepository.sendMessage(event.message)
                }
            }
        }
    }

    class Factory(private val chatRepository: ChatRepository) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            return when (screen) {
                ChatRoomScreen -> ChatRoomPresenter(chatRepository)
                else -> null
            }
        }
    }
}
