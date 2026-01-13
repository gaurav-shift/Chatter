package com.example.chatter.Presentation.ChatUi

import android.net.Uri
import com.example.chatter.DomainLayer.usecase.SendImageMessageUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.DomainLayer.usecase.GetMessageUseCase
import com.example.chatter.DomainLayer.usecase.SendMessageUseCase
import com.example.chatter.DomainLayer.util.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendImageMessageUseCase: SendImageMessageUseCase

) : ViewModel(){
    private val _messageState = MutableStateFlow<Results<List<Message>>>(Results.Loading)
    val messageState = _messageState.asStateFlow()

    fun getMessages(channelId: String){
        viewModelScope.launch {
            getMessagesUseCase(channelId).collect{
                result->
                _messageState.value = result
            }
        }
    }

    fun sendMessage(channelId: String, message: Message){
        viewModelScope.launch {
            sendMessageUseCase(channelId,message)
        }
    }
    fun sendImage(channelId: String, imageUri: Uri) {
        viewModelScope.launch {
            sendImageMessageUseCase(channelId, imageUri)
        }
    }

}