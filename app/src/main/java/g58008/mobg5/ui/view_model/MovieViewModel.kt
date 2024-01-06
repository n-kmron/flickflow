package g58008.mobg5.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g58008.mobg5.network.MovieApi
import g58008.mobg5.network.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

private const val TAG: String = "MOVIE_VIEW_MODEL"

class MovieViewModel : ViewModel() {

        val appViewModel = AppViewModel()

        var appUiState = appViewModel._uiState
            private set


        fun getRandomMovie() {
            viewModelScope.launch {
                try {
                    val response: Response<MovieResponse> = MovieApi.retrofitService.getRandomMovie()

                    if (response.isSuccessful) {
                        val movieResponse: MovieResponse? = response.body()
                        Log.d(TAG, "getRandomMovie - success: $movieResponse")
                    } else {
                        Log.e(TAG, "getRandomMovie - failed with code: ${response.code()}")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "getRandomMovie - failed: ${e.message}")
                }
            }
        }
}