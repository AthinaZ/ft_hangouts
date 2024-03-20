package com.example.ft_hangouts.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.ui.components.ContactItem
import com.example.ft_hangouts.viewmodel.ContactViewModel
import com.example.ft_hangouts.data.entity.ContactEntity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextAlign

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactListScreen(navController: NavController, viewModel: ContactViewModel, context: Context) {
    val contacts: List<ContactEntity> by viewModel.allContacts.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hangouts") },
                backgroundColor = MaterialTheme.colors.secondary,
                elevation = 8.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_contact")
                },
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 56.dp),
                containerColor = MaterialTheme.colors.secondary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (contacts.isNullOrEmpty()) {
                Text(
                    text = "No Contacts",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f) // Fill remaining space
                ) {
                    items(contacts) { contact ->
                        ContactItem(
                            context = context,
                            contact = contact,
                            onItemClick = {
                                navController.navigate("edit_contact/${contact.id}") },
                            onMessageClick = {
                                navController.navigate("message_compose/${contact.id}")
                            },
                            onCallClick = {
                                // Handle call initiation here
                            }
                        )
                    }
                }
            }
        }
    }
}

