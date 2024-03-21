package com.example.ft_hangouts.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ft_hangouts.data.entity.CallEntity

@Dao
interface CallDao {
    @Query("SELECT * FROM calls ORDER BY timestamp DESC")
    fun getAllCalls(): LiveData<List<CallEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCall(call: CallEntity)

    @Delete
    suspend fun deleteCall(call: CallEntity)

    @Query("DELETE FROM calls")
    suspend fun deleteAllCalls()

    // Add other methods as needed
}
