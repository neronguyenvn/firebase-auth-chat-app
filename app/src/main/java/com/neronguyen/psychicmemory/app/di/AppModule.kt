package com.neronguyen.psychicmemory.app.di

import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import com.neronguyen.psychicmemory.feature.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SignInClient> { Identity.getSignInClient(androidContext()) }
    single { GoogleAuthClient(androidContext(), get()) }
    viewModel { AuthViewModel(get()) }
}