package com.example.chatter.Navigation

import kotlinx.serialization.Serializable

@Serializable
sealed  class Routes {

    @Serializable
    data object Login : Routes()

    @Serializable
    data object SignUp : Routes()

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data class Chat(val channelId: String) : Routes()

}