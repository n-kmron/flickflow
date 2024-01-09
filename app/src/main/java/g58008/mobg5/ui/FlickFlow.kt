package g58008.mobg5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import g58008.mobg5.R
import g58008.mobg5.database.MovieItem
import g58008.mobg5.model.Repository
import g58008.mobg5.network.ReleaseDate
import g58008.mobg5.ui.view_model.AppViewModel
import g58008.mobg5.ui.view_model.MovieCallUiState
import g58008.mobg5.ui.view_model.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val movieViewModel: MovieViewModel = viewModel()
    val movieUiState by movieViewModel.appUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isDetailsVisible by remember { mutableStateOf(false) }

    when (movieViewModel.movieCallUiState) {
        is MovieCallUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is MovieCallUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
        else -> {}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_large))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (movieUiState.isMoviePresent) {
            DisplayMovieResume(
                title = movieUiState.movieTitle.text,

                imageUrl = movieUiState.movieImageUrl.url,
            )
            if (isDetailsVisible) {
                DisplayMovieDetails(
                    position = movieUiState.moviePosition,
                    releaseDate = movieUiState.movieReleaseDate,
                    updateFavourite = {
                        coroutineScope.launch {
                            movieViewModel.updateFavourite(movieUiState.movieId)
                        }
                    }
                )
                CustomButton(
                    onCustomButtonClick = {
                        isDetailsVisible = false
                    },
                    text = stringResource(R.string.hide_details)
                )
            } else {
                CustomButton(
                    onCustomButtonClick = {
                        isDetailsVisible = true
                    },
                    text = stringResource(R.string.see_details)
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.no_movie),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large))
            )
        }
        if (!isDetailsVisible) {
            SpinButton(spin = {
                coroutineScope.launch {
                    movieViewModel.getRandomMovie()
                }
            })
        }
    }
}

@Composable
fun DisplayMovieResume(
    title: String,
    imageUrl: String,
) {
    Column(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.cover_image_height))
        )

        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DisplayMovieDetails(
    position: Int,
    releaseDate: ReleaseDate,
    updateFavourite: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .fillMaxSize()
            //center the column
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(R.string.box_office_rating, position),
            style = MaterialTheme.typography.displayMedium,
        )

        Text(
            text = stringResource(
                R.string.release_date,
                releaseDate.day,
                releaseDate.month,
                releaseDate.year
            ),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        FavouriteButton(
            onClick = { updateFavourite() },
            color = Color.Yellow
        )
    }
}

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier
) {
    val appUiState = AppViewModel.getInstance().uiState
    var favouriteList by remember { mutableStateOf<List<MovieItem>>(emptyList()) }

    LaunchedEffect(appUiState) {
        favouriteList = Repository.getFavourites(appUiState.value.currentEmail)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (favouriteList.isNotEmpty()) {
            LazyColumn(modifier = modifier) {

                items(favouriteList.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = shapes.medium
                            )
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = favouriteList[index].movieTitle,
                            style = MaterialTheme.typography.displayMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.favourite_title_display))
                        )
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomButton(
                                onCustomButtonClick = { /*TODO*/ },
                                text = stringResource(R.string.see_details)
                            )
                            FavouriteButton(
                                onClick = {},
                                color = Color.Yellow
                            )
                        }
                    }
                }
            }
        } else
            Text(
                text = stringResource(id = R.string.no_favourites),
                textAlign = TextAlign.Center
            )
    }
}

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.author),
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.id),
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.group),
            fontSize = 16.sp,
        )
    }
}

@Composable
fun SpinButton(
    spin: () -> Unit,
) {
    Button(
        onClick = { spin() },
    ) {
        Text(text = stringResource(R.string.spin), fontSize = 16.sp)
    }
}

@Composable
fun FavouriteButton(
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = { onClick() },
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(16.dp)
        )
    }
}

@Composable
fun CustomButton(
    onCustomButtonClick: () -> Unit,
    text: String
) {
    Button(
        onClick = { onCustomButtonClick() },
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

