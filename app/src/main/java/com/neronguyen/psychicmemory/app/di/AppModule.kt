package com.neronguyen.psychicmemory.app.di

import com.neronguyen.psychicmemory.core.auth.di.authModule
import com.neronguyen.psychicmemory.core.common.coroutine.coroutineModule
import com.neronguyen.psychicmemory.core.data.di.dataModule
import com.neronguyen.psychicmemory.core.network.di.networkModule
import org.koin.dsl.module

val appModule = module {
    includes(
        coroutineModule,
        authModule,
        networkModule,
        dataModule,
        circuitModule
    )
}