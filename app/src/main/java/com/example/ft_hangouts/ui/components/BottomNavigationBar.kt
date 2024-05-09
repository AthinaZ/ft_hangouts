package com.example.ft_hangouts.ui.components

//import androidx.compose.ui.text.intl.Locale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.util.Locale

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        val screens = listOf("Contacts", "Messages", "Calls")
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.lowercase(Locale.ROOT),
                onClick = {
                    navController.navigate(screen.lowercase(Locale.ROOT)) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    // Icons for each screen
                    when (screen) {
                        "Contacts" -> Icon(Icons.Default.Person, contentDescription = "Contacts Icon")
                        "Messages" -> Icon(Icons.AutoMirrored.Filled.Message, contentDescription = "Messages Icon")
                        "Calls" -> Icon(Icons.Default.Call, contentDescription = "Calls Icon")
                    }
                },
//                label = { Text(screen) }
            )
        }
    }
}







