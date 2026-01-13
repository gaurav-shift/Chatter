package com.example.chatter.di

import com.example.chatter.DomainLayer.repository.AuthRepository
import com.example.chatter.DataLayer.RepositoryImpl.AuthRepoImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRepoImpl: AuthRepoImpl
    ): AuthRepository = authRepoImpl
}
