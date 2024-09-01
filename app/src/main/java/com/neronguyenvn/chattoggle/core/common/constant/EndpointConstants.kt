package com.neronguyenvn.chattoggle.core.common.constant

import com.neronguyenvn.chattoggle.BuildConfig

object Endpoint {
    val CHAT_WEBSOCKET = "${BuildConfig.BASE_URL.replace("http", "ws")}/chat"
    const val CHAT_HISTORY = "${BuildConfig.BASE_URL}/chatHistory"
}
