package com.neronguyen.psychicmemory.core.common.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineModule = module {
    single<CoroutineDispatcher>(named(ApplicationDispatcher.Default)) { Dispatchers.Default }
    single<CoroutineDispatcher>(named(ApplicationDispatcher.IO)) { Dispatchers.IO }
}

enum class ApplicationDispatcher {
    Default,
    IO,
}

