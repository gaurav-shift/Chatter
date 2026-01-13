package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.repository.AuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() : Boolean{
        return repository.isUserLoggedIn()
    }
}