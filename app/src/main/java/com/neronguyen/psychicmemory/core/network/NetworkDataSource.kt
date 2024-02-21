package com.neronguyen.psychicmemory.core.network

import kotlinx.coroutines.flow.Flow


interface NetworkDataSource {

    suspend fun connectToSocket(url: String, token: String): Flow<String>

    suspend fun sendMessage(message: String)

    suspend fun disconnectSocket()
}