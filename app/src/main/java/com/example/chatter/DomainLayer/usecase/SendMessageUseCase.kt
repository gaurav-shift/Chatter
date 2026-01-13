package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.DomainLayer.repository.MessageRepository
import com.example.chatter.DomainLayer.util.Results
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(
        channelId: String,
        message: Message
    ): Results<Unit>{
        return repository.sendMessage(channelId,message)
    }
}