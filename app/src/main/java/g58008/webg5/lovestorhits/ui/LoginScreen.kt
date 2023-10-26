package g58008.webg5.lovestorhits.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import g58008.webg5.lovestorhits.R

@Composable
fun LoginScren() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = "Login page background",
            modifier = Modifier.fillMaxSize().blur(6.dp),
            contentScale = ContentScale.Crop
            )
    }
}