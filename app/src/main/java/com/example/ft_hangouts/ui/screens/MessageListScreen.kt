package com.example.ft_hangouts.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.data.entity.MessageEntity
import com.example.ft_hangouts.viewmodel.MessageViewModel
import java.text.DateFormat
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MessageListScreen(navController: NavController, viewModel: MessageViewModel) {
    val messages: List<MessageEntity> by viewModel.messages.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Conversation History") },
                backgroundColor = MaterialTheme.colors.secondary,
                elevation = 8.dp
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (messages.isEmpty()) {
                    Text(
                        text = "No messages",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LazyColumn {
                        items(messages) { message ->
                            MessageItem(message = message)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MessageItem(message: MessageEntity) {
    // customize the message item UI here
    Text(
        text = "${message.sender}: ${message.content}",
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(8.dp)
    )
}


