package com.neronguyen.psychicmemory.core.common.constant

import com.neronguyen.psychicmemory.BuildConfig

object Endpoint {
    val CHAT_WEBSOCKET = "${BuildConfig.BASE_URL.replace("http", "ws")}/chat"
    const val CHAT_HISTORY = "${BuildConfig.BASE_URL}/chatHistory"
}
