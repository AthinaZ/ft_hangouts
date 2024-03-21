package com.example.ft_hangouts.data.repository

import androidx.lifecycle.LiveData
import com.example.ft_hangouts.data.dao.CallDao
import com.example.ft_hangouts.data.entity.CallEntity

class CallRepository(private val callDao: CallDao) {

    val allCalls: LiveData<List<CallEntity>> = callDao.getAllCalls()

//    fun getAllCalls(): LiveData<List<CallEntity>> {
//        return callDao.getAllCalls()
//    }

    suspend fun insertCall(call: CallEntity) {
        callDao.insertCall(call)
    }

    suspend fun deleteCall(call: CallEntity) {
        callDao.deleteCall(call)
    }

    // Add other methods as needed; delete calls, update calls, etc.
}
