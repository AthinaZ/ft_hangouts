package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ft_hangouts.di.AppModule
import com.example.ft_hangouts.ui.components.BottomNavigationBar
import com.example.ft_hangouts.ui.screens.AddEditContactScreen
import com.example.ft_hangouts.ui.screens.CallListScreen
import com.example.ft_hangouts.ui.screens.ContactListScreen
import com.example.ft_hangouts.ui.screens.MessageComposeScreen
import com.example.ft_hangouts.ui.screens.MessageListScreen
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme
import com.example.ft_hangouts.viewmodel.CallViewModel
import com.example.ft_hangouts.viewmodel.CallViewModelFactory
import com.example.ft_hangouts.viewmodel.ContactViewModel
import com.example.ft_hangouts.viewmodel.ContactViewModelFactory
import com.example.ft_hangouts.viewmodel.MessageViewModel
import com.example.ft_hangouts.viewmodel.MessageViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactApp(context = applicationContext)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactApp(context: Context) {
    val navController = rememberNavController()
    val contactRepository = AppModule.provideContactRepository(context)
    val messageRepository = AppModule.provideMessageRepository(context)
    val callRepository = AppModule.provideCallRepository(context)
    val contactViewModelFactory = ContactViewModelFactory(contactRepository)
    val messageViewModelFactory = MessageViewModelFactory(messageRepository)
    val callViewModelFactory = CallViewModelFactory(callRepository)
    val contactViewModel: ContactViewModel = viewModel(factory = contactViewModelFactory)
    val messageViewModel: MessageViewModel = viewModel(factory = messageViewModelFactory)
    val callContactViewModel: CallViewModel = viewModel(factory = callViewModelFactory)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) {
        NavHost(navController = navController, startDestination = "contacts") {
            composable("contacts") {
                ContactListScreen(navController = navController, viewModel = contactViewModel, context = context)
            }
            composable("add_contact") {
                AddEditContactScreen(navController = navController, viewModel = contactViewModel)
            }
            composable(
                route = "edit_contact/{contactId}",
                arguments = listOf(navArgument("contactId") { type = NavType.IntType }),
                content = { backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val contactId = arguments.getInt("contactId")
                    AddEditContactScreen(navController = navController, viewModel = contactViewModel, contactId = contactId)
                }
            )
            composable("messages") {
                MessageListScreen(navController = navController, viewModel = messageViewModel)
            }
            composable(
                route = "message_compose/{contactId}",
                arguments = listOf(navArgument("contactId") { type = NavType.IntType }),
                content = { backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val contactId = arguments.getInt("contactId")
                    MessageComposeScreen(navController = navController, viewModel = messageViewModel, contactId = contactId)
                }
            )
            composable("calls") {
                CallListScreen(navController = navController, viewModel = callContactViewModel)
            }
            // Other composable destinations
        }
    }
}
