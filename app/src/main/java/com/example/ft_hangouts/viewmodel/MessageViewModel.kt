package com.example.ft_hangouts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.entity.MessageEntity
import com.example.ft_hangouts.data.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MessageViewModelFactory(private val messageRepository: MessageRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class MessageViewModel(private val messageRepository: MessageRepository) : ViewModel() {

    // Flow to hold the list of messages
    val messages: Flow<List<MessageEntity>> = messageRepository.allMessages

    fun getConversation(contactId: Long, userId: Long): Flow<List<MessageEntity>> {
        return messageRepository.getConversation(contactId, userId)
    }


    // Function to insert a new message
    fun insertMessage(message: MessageEntity) {
        viewModelScope.launch {
            messageRepository.insertMessage(message)
        }
    }

    // Function to delete a message
    fun deleteMessage(message: MessageEntity) {
        viewModelScope.launch {
            messageRepository.deleteMessage(message)
        }
    }
}
