package com.example.chatter.di

import com.example.chatter.DataLayer.RepositoryImpl.ChannelRepoImpl
import com.example.chatter.DomainLayer.model.Channel
import com.example.chatter.DomainLayer.repository.ChannelRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChannelModule {
    @Provides
    @Singleton
    fun provideFirebaseDatabase() : FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideChannelRepository(
        database: FirebaseDatabase
    ): ChannelRepository{
        return ChannelRepoImpl(database)
    }
}