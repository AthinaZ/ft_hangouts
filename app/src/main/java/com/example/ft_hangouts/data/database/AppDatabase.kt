package com.example.ft_hangouts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ft_hangouts.data.dao.ContactDao
import com.example.ft_hangouts.data.dao.MessageDao
import com.example.ft_hangouts.data.entity.ContactEntity
import com.example.ft_hangouts.data.entity.MessageEntity


@Database(entities = [ContactEntity::class, MessageEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // handle migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

