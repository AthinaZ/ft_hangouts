package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.dao.MessageDao
import com.example.ft_hangouts.data.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {

    val allMessages: Flow<List<MessageEntity>> = messageDao.getAllMessages()

    fun getConversation(contactId: Long, userId: Long): Flow<List<MessageEntity>> {
        return messageDao.getConversation(contactId, userId)
    }

    suspend fun insertMessage(message: MessageEntity) {
        messageDao.insertMessage(message)
    }

    suspend fun deleteMessage(message: MessageEntity) {
        messageDao.deleteMessage(message)
    }

    suspend fun deleteAllMessages() {
        messageDao.deleteAllMessages()
    }
}
