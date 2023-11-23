package g58008.mobg5

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import g58008.mobg5.ui.AboutScreen
import g58008.mobg5.ui.HomeScreen
import g58008.mobg5.ui.LoginScreen

/**
 * A Composable function for the main entry point of the application. It sets up
 * navigation using a NavHost, starting with the "LOGIN" destination.
 **/
@Composable
fun App(

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.LOGIN.name,
    ) {
        composable(Navigation.LOGIN.name) {
            AppTemplate(navController = navController) {
                LoginScreen (
                    navigate = { navController.navigate(Navigation.HOME.name) }
                )
            }
        }
        composable(Navigation.HOME.name) {
            AppTemplate(navController = navController) {
                HomeScreen()
            }
        }
        composable(Navigation.ABOUT.name) {
            AppTemplate(navController = navController) {
                AboutScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTemplate(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val currentScreen = remember(navController) {
        derivedStateOf {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            Navigation.values().firstOrNull { it.name == currentRoute } ?: Navigation.LOGIN
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = { AppTopbar(scrollBehavior = scrollBehavior) },
        bottomBar = {
            if (currentScreen.value != Navigation.LOGIN) {
                BottomAppBar {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(
                            onClick = { navController.navigate(Navigation.HOME.name) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Home, contentDescription = "Home"
                            )
                        }

                        IconButton(
                            onClick = { navController.navigate(Navigation.ABOUT.name) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Info, contentDescription = "Home"
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            )
            {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size)),
                    painter = painterResource(R.drawable.esi_logo),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                )
            }
        },
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large))
    )
}