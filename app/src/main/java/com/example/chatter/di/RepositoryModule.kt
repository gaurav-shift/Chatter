package com.example.chatter.di

import com.example.chatter.DataLayer.RepositoryImpl.MessageRepoImpl
import com.example.chatter.DomainLayer.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        impl: MessageRepoImpl
    ): MessageRepository
}