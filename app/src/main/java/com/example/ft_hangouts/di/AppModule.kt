package com.example.ft_hangouts.di

import android.content.Context
import com.example.ft_hangouts.data.database.AppDatabase
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.repository.ContactRepositoryImpl
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.data.repository.CallRepository

object AppModule {
    private lateinit var contactRepository: ContactRepository
    private lateinit var messageRepository: MessageRepository
    private lateinit var callRepository: CallRepository

    fun provideContactRepository(context: Context): ContactRepository {
        if (!::contactRepository.isInitialized) {
            val database = AppDatabase.getInstance(context)
            val contactDao = database.contactDao()
            contactRepository = ContactRepositoryImpl(contactDao)
        }
        return contactRepository
    }

    fun provideMessageRepository(context: Context): MessageRepository {
        if (!::messageRepository.isInitialized) {
            val database = AppDatabase.getInstance(context)
            val messageDao = database.messageDao()
            messageRepository = MessageRepository(messageDao)
        }
        return messageRepository
    }

    fun provideCallRepository(context: Context): CallRepository {
        if (!::callRepository.isInitialized) {
            val database = AppDatabase.getInstance(context)
            val callDao = database.callDao()
            callRepository = CallRepository(callDao)
        }
        return callRepository
    }
}
