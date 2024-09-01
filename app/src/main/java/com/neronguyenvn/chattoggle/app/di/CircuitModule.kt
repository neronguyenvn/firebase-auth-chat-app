package com.neronguyenvn.chattoggle.app.di

import com.neronguyenvn.chattoggle.feature.auth.AuthPresenter
import com.neronguyenvn.chattoggle.feature.auth.AuthScreen
import com.neronguyenvn.chattoggle.feature.auth.AuthUi
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomPresenter
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomScreen
import com.neronguyenvn.chattoggle.feature.chatroom.ChatRoomUi
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