package com.example.chatter.Presentation.HomeUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.util.CoilUtils.result
import com.example.chatter.DomainLayer.model.Channel
import com.example.chatter.DomainLayer.usecase.AddChannelUseCase
import com.example.chatter.DomainLayer.usecase.GetChannelsUseCase
import com.example.chatter.DomainLayer.util.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChannelsUseCase: GetChannelsUseCase,
    private val addChannelUseCase: AddChannelUseCase
) : ViewModel() {
    private val _channelsState = MutableStateFlow<Results<List<Channel>>>(Results.Loading)
    val channelsState = _channelsState.asStateFlow()

    fun getChannels(){
        viewModelScope.launch {
            getChannelsUseCase().collect{ result->
                _channelsState.value = result
            }
        }
    }
    fun addChannel(name: String) {
        viewModelScope.launch {
            addChannelUseCase(name)
        }
    }
}