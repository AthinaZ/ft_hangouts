package com.example.ft_hangouts.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ft_hangouts.data.entity.ContactEntity

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Update
    suspend fun updateContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts ORDER BY firstname ASC")
    fun getAllContacts(): LiveData<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactById(contactId: Int): LiveData<ContactEntity?>
}
