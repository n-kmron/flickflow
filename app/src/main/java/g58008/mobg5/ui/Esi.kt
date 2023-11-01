package g58008.mobg5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import g58008.mobg5.R
import g58008.mobg5.ui.theme.AppTheme

@Composable
fun EsiScreen() {
    val image = painterResource(R.drawable.esi_logo)

    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Image(
            painter = image,
            contentDescription = "ESI LOGO",
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = false)
@Composable
fun EsiPreview() {
    AppTheme {
        EsiScreen()
    }
}