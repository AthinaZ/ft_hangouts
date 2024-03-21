package com.example.ft_hangouts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ft_hangouts.data.entity.CallEntity
import com.example.ft_hangouts.data.repository.CallRepository

class CallViewModelFactory(private val callRepository: CallRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CallViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CallViewModel(callRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CallViewModel(private val callRepository: CallRepository) : ViewModel() {

    val calls: LiveData<List<CallEntity>> = callRepository.allCalls

    fun makeCall(contactId: Int) {
        // make a call logic
    }

    // Add other functions as needed; delete call logs, etc.
}
