package com.neronguyen.psychicmemory.core.common.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@SuppressLint("ComposableNaming")
@Composable
fun LifecycleOwner.observeLifecycle(doOnStart: () -> Unit, doOnStop: () -> Unit) {
    DisposableEffect(key1 = this) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> doOnStart()
                Lifecycle.Event.ON_STOP -> doOnStop()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
}