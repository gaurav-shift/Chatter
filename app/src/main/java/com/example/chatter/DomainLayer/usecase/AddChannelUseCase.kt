package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.repository.ChannelRepository
import com.example.chatter.DomainLayer.util.Results
import javax.inject.Inject

class AddChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(name: String): Results<Unit> {
        return repository.addChannel(name)
    }
}