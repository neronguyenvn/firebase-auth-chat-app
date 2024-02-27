package com.neronguyen.psychicmemory.app.di

import com.neronguyen.psychicmemory.feature.auth.AuthPresenter
import com.neronguyen.psychicmemory.feature.auth.AuthScreen
import com.neronguyen.psychicmemory.feature.auth.AuthUi
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomPresenter
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomUi
import com.slack.circuit.foundation.Circuit
import org.koin.dsl.module

val circuitModule = module {
    single<Circuit> {
        Circuit.Builder()
            .addUi<AuthScreen, AuthScreen.State> { state, modifier -> AuthUi(state, modifier) }
            .addUi<ChatRoomScreen, ChatRoomScreen.State> { state, modifier ->
                ChatRoomUi(
                    state = state,
                    modifier = modifier
                )
            }
            .addPresenterFactories(
                listOf(
                    AuthPresenter.Factory(get()),
                    ChatRoomPresenter.Factory(get(), get())
                )
            )
            .build()
    }
}