package com.example.ft_hangouts.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.entity.ContactEntity
import com.example.ft_hangouts.data.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val allContacts = repository.getAllContacts()

    fun getContactById(contactId: Int): LiveData<ContactEntity?> {
        return repository.getContactById(contactId)
    }

    fun insertContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun deleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun updateContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }
}
