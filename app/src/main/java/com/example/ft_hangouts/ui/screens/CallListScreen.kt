package com.example.ft_hangouts.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.data.entity.CallEntity
import com.example.ft_hangouts.ui.components.CallItem
import com.example.ft_hangouts.viewmodel.CallViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CallListScreen(navController: NavController, viewModel: CallViewModel) {

    val calls: List<CallEntity> by viewModel.calls.observeAsState(emptyList())

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Call History") },
//                backgroundColor = MaterialTheme.colors.primary,
//                elevation = 8.dp,
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Conversation History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            CallList(calls = calls)
        }
    )
}

@Composable
fun CallList(calls: List<CallEntity>) {
        if (calls.isEmpty()) {
                Text(
                    text = "No calls",
                    modifier = Modifier
                        .padding(75.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
        } else {
            LazyColumn {
        items(calls) { call ->
            CallItem(call = call)
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }
        }
    }
}


