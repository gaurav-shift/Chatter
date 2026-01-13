package com.example.chatter.DataLayer.RepositoryImpl

import com.example.chatter.DomainLayer.repository.AuthRepository
import com.example.chatter.DomainLayer.util.Results
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Results<String> {
        return try{
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Results.Success("Login Successfully")
        }catch (e: Exception){
            Results.Failure(e.localizedMessage ?: "Unknown Error During login")
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Results<String> {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            firebaseAuth.signOut()
            Results.Success("SignUp Successful")
        } catch (e: Exception){
            Results.Failure(e.localizedMessage ?: "Unknown Error During SignUp")
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }


}