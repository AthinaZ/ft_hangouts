package com.example.ft_hangouts.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calls")
data class CallEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val callerName: String,
    val callerNumber: String,
    val callType: CallType,
    val timestamp: Long,
    val durationSeconds: Int
)

enum class CallType {
    INCOMING,
    OUTGOING,
    MISSED
}
