package com.neronguyenvn.chattoggle.core.firebase.auth.di

import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.neronguyenvn.chattoggle.core.firebase.auth.GoogleAuthClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {
    single<SignInClient> { Identity.getSignInClient(androidContext()) }
    single { GoogleAuthClient(androidContext(), get()) }
}
