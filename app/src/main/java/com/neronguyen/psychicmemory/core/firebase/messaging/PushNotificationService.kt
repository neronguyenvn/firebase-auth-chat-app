package com.neronguyen.psychicmemory.core.firebase.messaging

import com.google.firebase.messaging.FirebaseMessagingService

class PushNotificationService  : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // TODO update user token on server
    }
}