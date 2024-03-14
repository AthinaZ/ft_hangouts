package com.example.ft_hangouts.data.model

data class ContactModel(
    val id: Int = 0,                // Unique ID for the contact
    val firstname: String,          // First Name of the contact
    val lastname: String,           // Last Name of the contact
    val phoneNumber: String,        // Contact's phone number
    val email: String,              // Contact's email address
    val address: String = "",       // Contact's physical address
    val notes: String = "",         // Additional notes or details about the contact
    val imageUri: String? = null    // Nullable, as the image might not always be available
)