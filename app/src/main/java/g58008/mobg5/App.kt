package g58008.mobg5

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import g58008.mobg5.ui.EsiScreen
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
            LoginScreen(
                navigate = { navController.navigate(Navigation.HOME.name) }
            )
        }
        composable(Navigation.HOME.name) {
            EsiScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopbar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
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