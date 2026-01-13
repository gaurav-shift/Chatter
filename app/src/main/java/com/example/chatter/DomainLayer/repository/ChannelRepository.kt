package com.example.chatter.DomainLayer.repository

import com.example.chatter.DomainLayer.model.Channel
import com.example.chatter.DomainLayer.util.Results
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
     fun getChannels() : Flow<Results<List<Channel>>>
     suspend fun addChannel(name: String): Results<Unit>
}