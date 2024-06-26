package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.entity.CallEntity
import com.example.ft_hangouts.data.entity.CallType

@Composable
fun CallItem(call: CallEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display call type icon based on call type (incoming, outgoing, missed)
        val callTypeIcon = when (call.callType) {
            CallType.INCOMING -> Icons.AutoMirrored.Filled.CallReceived
            CallType.OUTGOING -> Icons.AutoMirrored.Filled.CallMade
            CallType.MISSED -> Icons.AutoMirrored.Filled.CallMissed
        }
        Icon(
            imageVector = callTypeIcon,
            contentDescription = "Call Type Icon",
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Display phone number and call duration
        Column {
            Text(text = call.callerName)
            Text(text = call.callerNumber)
            Text(text = call.timestamp.toString())
            if (call.durationSeconds != null) {
                Text(text = "Duration: ${call.durationSeconds} seconds")
            }
        }
    }
}