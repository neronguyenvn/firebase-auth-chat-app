package com.neronguyenvn.chattoggle.app.di

import com.neronguyenvn.chattoggle.core.common.coroutine.coroutineModule
import com.neronguyenvn.chattoggle.core.data.di.dataModule
import com.neronguyenvn.chattoggle.core.database.di.databaseModule
import com.neronguyenvn.chattoggle.core.firebase.auth.di.authModule
import com.neronguyenvn.chattoggle.core.network.di.networkModule
import org.koin.dsl.module

val appModule = module {
    includes(
        coroutineModule,
        authModule,
        networkModule,
        dataModule,
        circuitModule,
        databaseModule
    )
}
