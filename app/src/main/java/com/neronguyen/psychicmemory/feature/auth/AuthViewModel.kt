package com.neronguyen.psychicmemory.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import com.neronguyen.psychicmemory.core.model.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AuthUiState(val userData: UserData? = null)

class AuthViewModel(private val googleAuthClient: GoogleAuthClient) : ViewModel() {
    val uiState = googleAuthClient.currentUser
        .map { AuthUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun updateAuthUser() {
        viewModelScope.launch {
            googleAuthClient.updateCurrentUser()
        }
    }
}