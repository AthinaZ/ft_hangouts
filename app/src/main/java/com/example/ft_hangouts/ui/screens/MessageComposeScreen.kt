package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.data.entity.MessageEntity
import com.example.ft_hangouts.viewmodel.MessageViewModel

@Composable
fun MessageComposeScreen(
    navController: NavController,
    viewModel: MessageViewModel,
    contactId: Int,
//    userId: Int
) {
    var messageText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            label = { Text("Type your message") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (messageText.isNotEmpty()) {
                    val message = MessageEntity(
                        sender = "You", // Assuming sender is the current user
                        receiver = contactId.toString(), // Assuming receiver is the contact's ID
                        content = messageText,
                        timestamp = System.currentTimeMillis() // Assuming timestamp is current time
                    )
                    viewModel.insertMessage(message)
                    messageText = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Send")
        }
    }
}
