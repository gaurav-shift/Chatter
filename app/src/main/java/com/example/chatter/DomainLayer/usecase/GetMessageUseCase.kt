package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.repository.MessageRepository
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(channelId : String) = repository.getMessages(channelId)
}