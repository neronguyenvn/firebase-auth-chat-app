package com.neronguyen.psychicmemory.core.data.di

import com.neronguyen.psychicmemory.core.common.coroutine.ApplicationDispatcher
import com.neronguyen.psychicmemory.core.data.ChatRepository
import com.neronguyen.psychicmemory.core.data.implementation.DefaultChatRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<ChatRepository> { DefaultChatRepository(get(), get(named(ApplicationDispatcher.IO))) }
}