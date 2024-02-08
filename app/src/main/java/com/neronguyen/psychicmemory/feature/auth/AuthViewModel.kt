package com.neronguyen.psychicmemory.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import com.neronguyen.psychicmemory.core.model.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class AuthUiState(val userData: UserData? = null)

class AuthViewModel(googleAuthClient: GoogleAuthClient) : ViewModel() {
    val uiState = googleAuthClient.currentUser
        .map { AuthUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
}