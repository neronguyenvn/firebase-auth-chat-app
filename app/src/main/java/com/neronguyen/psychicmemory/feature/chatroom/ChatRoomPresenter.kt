package com.neronguyen.psychicmemory.feature.chatroom

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen

class ChatRoomPresenter : Presenter<ChatRoomScreen.State> {
    @Composable
    override fun present(): ChatRoomScreen.State {
        return ChatRoomScreen.State(emptyList())
    }

    class Factory : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            return when(screen) {
                ChatRoomScreen -> ChatRoomPresenter()
                else -> null
            }
        }
    }
}