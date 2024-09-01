package com.neronguyenvn.chattoggle.core.data.di

import com.neronguyenvn.chattoggle.core.common.coroutine.ApplicationDispatcher
import com.neronguyenvn.chattoggle.core.data.ChatRepository
import com.neronguyenvn.chattoggle.core.data.implementation.OfflineFirstChatRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<ChatRepository> {
        OfflineFirstChatRepository(
            get(),
            get(),
            get(named(ApplicationDispatcher.IO))
        )
    }
}