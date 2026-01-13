package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.repository.MessageRepository
import com.example.chatter.DomainLayer.util.Results
import javax.inject.Inject

class DeleteMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(
        channelId: String,
        messageId: String
    ): Results<Unit> {
        return repository.deleteMessage(channelId, messageId)
    }
}
