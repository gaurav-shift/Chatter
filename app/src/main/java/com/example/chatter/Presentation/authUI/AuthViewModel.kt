package com.example.chatter.Presentation.authUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatter.DomainLayer.usecase.IsUserLoggedInUseCase
import com.example.chatter.DomainLayer.usecase.LoginUsecase
import com.example.chatter.DomainLayer.usecase.SIgnUpUsecase
import com.example.chatter.DomainLayer.util.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val signUpUsecase: SIgnUpUsecase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase

) : ViewModel(){
    private val _authState = MutableStateFlow<Results<String>>(Results.Idle)
    val authState = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun login(email: String, password: String){
        _authState.value = Results.Loading
        viewModelScope.launch(Dispatchers.IO){
            val result = loginUsecase(email,password)
            _authState.value =result

        }
    }

    fun signUp(email: String,password: String){
        _authState.value = Results.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = signUpUsecase(email , password)
            _authState.value = result
        }
    }
    fun resetAuthState(){
        _authState.value = Results.Idle
    }
    fun checkUserSession(){
        _isLoggedIn.value = isUserLoggedInUseCase()
    }
}