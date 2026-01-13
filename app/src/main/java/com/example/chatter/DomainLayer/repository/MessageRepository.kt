package com.example.chatter.DomainLayer.repository

import android.net.Uri
import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.DomainLayer.util.Results
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(
        channelId: String
    ): Flow<Results<List<Message>>>

    suspend fun sendMessage(
        channelId: String,
        message: Message
    ): Results<Unit>
    suspend fun sendImageMessage(
        channelId: String,
        imageUri: Uri
    ) : Results<Unit>
    suspend fun deleteMessage(
        channelId: String,
        messageId: String
    ): Results<Unit>

}