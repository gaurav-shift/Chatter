package com.example.chatter.DomainLayer.usecase

import com.example.chatter.DomainLayer.repository.AuthRepository
import com.example.chatter.DomainLayer.util.Results
import javax.inject.Inject

class SIgnUpUsecase @Inject constructor(private val repository: AuthRepository){
    suspend operator fun invoke(email: String, password: String) : Results<String>{
        return repository.signup(email,password)
    }
}