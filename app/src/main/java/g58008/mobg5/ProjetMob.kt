package g58008.mobg5

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import g58008.mobg5.ui.EsiScreen
import g58008.mobg5.ui.LoginScreen
import g58008.mobg5.ui.NavigationDestinations

/**
 * A Composable function for the main entry point of the "ProjetMob" application. It sets up
 * navigation using a NavHost, starting with the "LOGIN" destination.
 **/
@Composable
fun ProjetMob(

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.LOGIN.name,
    ) {
        composable(NavigationDestinations.LOGIN.name) {
            LoginScreen(
                navController = navController,
            )
        }
        composable(NavigationDestinations.HOME.name) {
            EsiScreen()
        }
    }
}