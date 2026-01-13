package com.example.chatter.DomainLayer.repository

import com.example.chatter.DomainLayer.util.Results

interface AuthRepository {
    suspend fun login(email: String,password: String) : Results<String>
    suspend fun signup(email: String,password: String) : Results<String>
    fun isUserLoggedIn() : Boolean
}