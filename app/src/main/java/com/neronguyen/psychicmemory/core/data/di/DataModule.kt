package com.neronguyen.psychicmemory.core.data.di

import com.neronguyen.psychicmemory.core.common.coroutine.ApplicationDispatcher
import com.neronguyen.psychicmemory.core.data.ChatRepository
import com.neronguyen.psychicmemory.core.data.implementation.OfflineFirstChatRepository
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