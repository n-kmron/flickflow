package g58008.mobg5.ui.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g58008.mobg5.database.MovieItem
import g58008.mobg5.model.Repository
import g58008.mobg5.network.Image
import g58008.mobg5.network.MovieApi
import g58008.mobg5.network.MovieResult
import g58008.mobg5.network.ReleaseDate
import g58008.mobg5.network.TitleText
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

private const val TAG: String = "MOVIE_VIEW_MODEL"

sealed interface MovieCallUiState {
    data class Success(val response: String) : MovieCallUiState
    object Error : MovieCallUiState
    object Loading : MovieCallUiState
    object Empty : MovieCallUiState
}

/**
 * MovieViewModel is a singleton class as an object.
 */
object MovieViewModel : ViewModel() {

    private val appViewModel = AppViewModel.getInstance()

    var appUiState = appViewModel.uiState
        private set
    var movieCallUiState: MovieCallUiState by mutableStateOf(MovieCallUiState.Empty)
        private set

    val favouriteMovies: MutableState<List<MovieItem>> = mutableStateOf(listOf())

    private fun updateMovieState(
        id: String,
        title: TitleText,
        image: Image,
        releaseDate: ReleaseDate,
        rating: Double,
        voteCount: Int,
        plot: String,
        genre: String
    ) {
        appUiState.value = appUiState.value.copy(
            isMoviePresent = true,
            movieId = id,
            movieImageUrl = image,
            movieTitle = title,
            movieReleaseDate = releaseDate,
            movieGender = genre,
            movieRating = rating,
            movieVoteCount = voteCount,
            moviePlot = plot
        )
        Log.d(TAG, "uiState updated: ${appUiState.value}")
    }

    suspend fun getMovie(movieId: String) {
        viewModelScope.launch {
            movieCallUiState = apiCall(
                apiCall = { MovieApi.retrofitService.getMovie(movieId) },
                handleResponse = { response ->
                    response?.results
                }
            )
        }
    }

    suspend fun getRandomMovie() {
        viewModelScope.launch {
            movieCallUiState = apiCall(
                apiCall = { MovieApi.retrofitService.getRandomMovie() },
                handleResponse = { response ->
                    response?.results?.get(0)
                }
            )
        }
    }

    /**
     * Update favourites of the current user
     * @if the movie is already in the favourites, delete it
     * @else add it
     */
    fun updateFavourite(movieId: String) {
        viewModelScope.launch {
            //if repo.getFavourite(movieId, currentEmail) exists
            if (Repository.getFavouriteWithId(appUiState.value.currentEmail, movieId) != null) {
                Repository.deleteFavourite(movieId, appUiState.value.currentEmail)
            } else {
                Repository.addFavourite(
                    movieId,
                    appUiState.value.movieTitle.text,
                    appUiState.value.currentEmail
                )
            }
            favouriteMovies.value = Repository.getFavourites(appUiState.value.currentEmail)

        }
    }

    /**
     * Update the game state based on the user data provided by searching a record corresponding to the data.
     * R is the type of the response body -> in function of if it's the random or the id call, the response body is in a list or not so we need to get the first element of the list
     * T is the type of the response -> in function of if it's the random or the id call, the response is a list or not
     */
    private suspend fun <T, R> apiCall(
        apiCall: suspend () -> Response<T>,
        handleResponse: (T?) -> R
    ): MovieCallUiState {
        return try {
            movieCallUiState = MovieCallUiState.Loading
            val response = apiCall()

            if (response.isSuccessful) {
                handleSuccessfulResponse(handleResponse(response.body()))
            } else {
                Log.e(TAG, "API call failed with code: ${response.code()}")
                return MovieCallUiState.Empty            }
        } catch (e: IOException) {
            Log.e(TAG, "API call failed: ${e.message}")
            return MovieCallUiState.Error
        }
    }

    private fun handleSuccessfulResponse(movieResult: Any?): MovieCallUiState {
        Log.d(TAG, "API call successful")
        if (movieResult is MovieResult) {
            updateMovieState(
                movieResult.id,
                movieResult.title,
                movieResult.image,
                movieResult.releaseDate,
                movieResult.ratingsSummary.aggregateRating,
                movieResult.ratingsSummary.voteCount,
                movieResult.plot.plotText.plainText,
                movieResult.genres.genres[0].text
            )
        }
        return MovieCallUiState.Success(movieResult.toString())
    }

    /**
     * Check if the movie is in the favourites of the current user
     */
    fun isFavourite(movieId: String): Boolean {
        return favouriteMovies.value.any { it.movieId == movieId }
    }

    init {
        viewModelScope.launch {
            favouriteMovies.value = Repository.getFavourites(appUiState.value.currentEmail)
        }
    }
}