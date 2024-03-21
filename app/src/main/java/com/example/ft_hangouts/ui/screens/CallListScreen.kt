package com.example.ft_hangouts.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CallListScreen(navController: NavController, viewModel: CallViewModel) {

    val calls: List<CallEntity> by viewModel.calls.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Call History") },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 8.dp,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
        if (calls.isNullOrEmpty()) {

                Text(
                    text = "No calls",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
        } else {
            LazyColumn {
        items(calls) { call ->
            CallItem(call = call)
            Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}


