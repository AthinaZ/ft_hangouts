package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.entity.ContactEntity

@Composable
fun ContactItem(
    contact: ContactEntity,
    onItemClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCallClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable (onClick = onItemClick)
    ) {
        // Display contact details (name, phone number, etc.)
        Text(text = contact.firstname + " ")
        Text(text = contact.lastname)

        // Call button
        IconButton(onClick = { onCallClick() }) {
            Icon(Icons.Default.Call, contentDescription = "Call")
        }

        // Message button
        IconButton(onClick = { onMessageClick() }) {
            Icon(Icons.Default.Message, contentDescription = "Message")
        }
    }
}


