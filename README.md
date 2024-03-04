# Chatne App

**Chatne** is a simple chat application built with
**[Jetpack Compose](https://developer.android.com/jetpack/compose)**
that allows authenticated Google users
to join a single shared chat room.

## 🔥 Motivation

This project was initiated to explore Firebase Authentication, WebSocket,
and Push Notifications. Initially focusing on building a basic chat app,
I aim to expand its functionality to include person-to-person chats and
options for connecting users with strangers. This ongoing development journey
is driven by a passion for learning and innovation.

## 🛠️ Technologies

- **UI**: Built with
  **[Jetpack Compose](https://developer.android.com/jetpack/compose)** and
  **[Coil](https://github.com/coil-kt/coil)** for image loading.

- **Architecture**: Utilizes **[Circuit](https://slackhq.github.io/circuit/)**
  for MVI Pattern + Navigation and Clean Architecture principles.

- **Dependency Injection**: Powered by **[Koin](https://github.com/InsertKoinIO/koin)**

- **Firebase**: Integrates **[Firebase Authentication](https://firebase.google.com/docs/auth)**
  for Google Sign-In and
  **[Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging)**
  for receiving push notifications.

- **Network**: Utilizes **[Ktor Client](https://github.com/ktorio/ktor)** for REST API
  and WebSocket communication.

- **Storage**: Implements **[Realm](https://github.com/realm)**
  for offline-first chat history storage.

- **Additional**: Utilizes **[Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime)**
  for easy serialize and deserialize Instant time.

## 📋 Features

- **Intuitive Interface**: Built using Jetpack Compose for a seamless chat experience.
- **Google Authentication**: Sign in with your Google account, with a sign-out
  option via your avatar.
- **Single Chat Room**: Connect with others in one shared space,
  with access to the entire chat history .

## 🎥 Demo
https://github.com/nero240399/firebase-auth-chat-app/assets/50225603/d09eec54-0efb-48d0-81c9-9da19c4e908d
