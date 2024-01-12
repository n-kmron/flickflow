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
import g58008.mobg5.network.MovieResponse
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

    private val favouriteMovies : MutableState<List<MovieItem>> = mutableStateOf(listOf())

    private fun updateMovieState(
        id: String,
        title: TitleText,
        image: Image,
        releaseDate: ReleaseDate,
        position: Int
    ) {
        appUiState.value = appUiState.value.copy(
            isMoviePresent = true,
            movieId = id,
            movieImageUrl = image,
            movieTitle = title,
            movieReleaseDate = releaseDate,
            moviePosition = position,
        )
        Log.d(TAG, "uiState updated: ${appUiState.value}")
    }

    suspend fun getRandomMovie() {
        viewModelScope.launch {

            movieCallUiState = MovieCallUiState.Loading
            movieCallUiState = try {
                val response: Response<MovieResponse> = MovieApi.retrofitService.getRandomMovie()

                if (response.isSuccessful) {
                    val movieResponse: MovieResult? = response.body()?.results?.get(0)
                    Log.d(TAG, "getRandomMovie : api call successful")
                    if (movieResponse != null) {
                        updateMovieState(
                            movieResponse.id,
                            movieResponse.title,
                            movieResponse.image,
                            movieResponse.releaseDate,
                            movieResponse.position,
                        )
                    }
                    MovieCallUiState.Success(movieResponse.toString())
                } else {
                    Log.e(TAG, "getRandomMovie - failed with code: ${response.code()}")
                    MovieCallUiState.Empty
                }
            } catch (e: IOException) {
                Log.e(TAG, "getRandomMovie - failed: ${e.message}")
                MovieCallUiState.Error
            }
        }
    }

    fun updateFavourite(movieId : String) {
        viewModelScope.launch {
            //if repo.getFavourite(movieId, currentEmail) exists
            if(Repository.getFavouriteWithId(appUiState.value.currentEmail, movieId) != null) {
                Repository.deleteFavourite(movieId, appUiState.value.currentEmail)
            } else {
                Repository.addFavourite(movieId, appUiState.value.movieTitle.text, appUiState.value.currentEmail)
            }
            favouriteMovies.value = Repository.getFavourites(appUiState.value.currentEmail)

        }
    }

    fun isFavourite(movieId : String) : Boolean {
        return favouriteMovies.value.any { it.movieId == movieId }
    }

    suspend fun getFavouritesFromCurrentUser(): List<MovieItem> {
        return Repository.getFavourites(appUiState.value.currentEmail)
    }

    init {
        viewModelScope.launch {
            favouriteMovies.value = Repository.getFavourites(appUiState.value.currentEmail)
        }
    }
}