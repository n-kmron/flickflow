package g58008.mobg5.ui

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import g58008.mobg5.R
import g58008.mobg5.database.MovieItem
import g58008.mobg5.network.ReleaseDate
import g58008.mobg5.ui.view_model.MovieCallUiState
import g58008.mobg5.ui.view_model.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    context: Context
) {
    val movieViewModel = remember { MovieViewModel }
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
                displayImage = !isDetailsVisible
            )
            if (isDetailsVisible) {
                DisplayMovieDetails(
                    isFavourite = movieViewModel.isFavourite(movieUiState.movieId),
                    rating = movieUiState.movieRating,
                    voteCount = movieUiState.movieVoteCount,
                    plot = movieUiState.moviePlot,
                    gender = movieUiState.movieGender,
                    releaseDate = movieUiState.movieReleaseDate,
                    updateFavourite = {
                        coroutineScope.launch {
                            movieViewModel.updateFavourite(movieUiState.movieId)
                        }
                    },
                    shareMovie = { movieViewModel.shareMovie(context) }
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DisplayMovieResume(
    title: String,
    imageUrl: String,
    displayImage: Boolean
) {
    Column(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
    ) {
        if (displayImage) {
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
        }
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
    isFavourite: Boolean,
    rating: Double,
    voteCount: Int,
    plot: String,
    gender: String,
    releaseDate: ReleaseDate,
    updateFavourite: () -> Unit,
    shareMovie: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = plot,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.rating, rating, voteCount),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.gender, gender),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
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
                .align(Alignment.CenterHorizontally)
        )
        FavouriteButton(
            onClick = { updateFavourite() },
            isFavourite = isFavourite
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = { shareMovie() },
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun FavouritesScreen(
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val movieViewModel = remember { MovieViewModel }
    val movieUiState by movieViewModel.appUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var favouriteList by remember { mutableStateOf<List<MovieItem>>(emptyList()) }

    LaunchedEffect(movieUiState) {
        favouriteList = movieViewModel.favouriteMovies.value
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
                                onCustomButtonClick = {
                                    coroutineScope.launch {
                                        movieViewModel.getMovie(favouriteList[index].movieId)
                                        navigate()
                                    }
                                },
                                text = stringResource(R.string.load)
                            )
                            FavouriteButton(
                                onClick = { movieViewModel.updateFavourite(favouriteList[index].movieId) },
                                isFavourite = movieViewModel.isFavourite(favouriteList[index].movieId)
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
    isFavourite: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = { onClick() },
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (isFavourite) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(16.dp)

            )
        }
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

