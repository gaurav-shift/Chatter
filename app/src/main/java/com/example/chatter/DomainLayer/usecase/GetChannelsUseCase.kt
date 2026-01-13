package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.repository.ChannelRepository
import javax.inject.Inject

class GetChannelsUseCase @Inject constructor (
    private val repository: ChannelRepository
) {
    operator fun invoke() = repository.getChannels()
}