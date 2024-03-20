package com.example.ft_hangouts.ui.components

//import androidx.compose.ui.text.intl.Locale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.util.Locale

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        val screens = listOf("Contacts", "Messages", "Calls")
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            BottomNavigationItem(
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
                        "Messages" -> Icon(Icons.Default.Message, contentDescription = "Messages Icon")
                        "Calls" -> Icon(Icons.Default.Call, contentDescription = "Calls Icon")
                    }
                },
//                label = { Text(screen) }
            )
        }
    }
}







