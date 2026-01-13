package com.example.chatter.DomainLayer.usecase

import android.net.Uri
import com.example.chatter.DomainLayer.repository.MessageRepository
import com.example.chatter.DomainLayer.util.Results
import javax.inject.Inject

class SendImageMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(channelId: String,imageUri: Uri): Results<Unit>{
        return repository.sendImageMessage(channelId,imageUri)
    }
}