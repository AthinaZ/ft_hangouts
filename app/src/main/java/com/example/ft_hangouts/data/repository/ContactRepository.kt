package com.example.ft_hangouts.data.repository

import androidx.lifecycle.LiveData
import com.example.ft_hangouts.data.dao.ContactDao
import com.example.ft_hangouts.data.entity.ContactEntity

interface ContactRepository {
    fun getAllContacts(): LiveData<List<ContactEntity>>
    fun getContactById(contactId: Int): LiveData<ContactEntity?>
    suspend fun insertContact(contact: ContactEntity)
    suspend fun updateContact(contact: ContactEntity)
    suspend fun deleteContact(contact: ContactEntity)
}

class ContactRepositoryImpl(private val contactDao: ContactDao) : ContactRepository {
    override fun getAllContacts(): LiveData<List<ContactEntity>> {
        return contactDao.getAllContacts()
    }

    override fun getContactById(contactId: Int): LiveData<ContactEntity?> {
        return contactDao.getContactById(contactId)
    }

    override suspend fun insertContact(contact: ContactEntity) {
        contactDao.insertContact(contact)
    }

    override suspend fun updateContact(contact: ContactEntity) {
        contactDao.updateContact(contact)
    }

    override suspend fun deleteContact(contact: ContactEntity) {
        contactDao.deleteContact(contact)
    }
}
