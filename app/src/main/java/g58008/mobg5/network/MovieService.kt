package g58008.mobg5.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

private const val BASE_URL =
    "https://moviesdatabase.p.rapidapi.com"

private const val API_KEY = "1b21635f61mshf06c07ab51577a4p1a1f71jsn97a13203cd00"

private var json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MovieService {
    /**
     * This request require a param "list" which can be:
     * - top_boxoffice_200
     * - titles
     * - and other here : https://rapidapi.com/SAdrian/api/moviesdatabase/details
     */
    @GET("titles/random?list=top_boxoffice_200&limit=1&info=base_info")
    suspend fun getRandomMovie(
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY,
        @Header("X-RapidAPI-Host") apiHost: String = "moviesdatabase.p.rapidapi.com"
    ) : Response<MovieListResponse>

    @GET("/titles/{id}?info=base_info")
    suspend fun getMovie(
        @Path("id") movieId: String,
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY,
        @Header("X-RapidAPI-Host") apiHost: String = "moviesdatabase.p.rapidapi.com"
    ) : Response<MovieResponse>
}

@Serializable
data class MovieListResponse(
    @SerialName("results")
    val results: List<MovieResult>
)

@Serializable
data class MovieResponse(
    @SerialName("results")
    val results: MovieResult
)

@Serializable
data class MovieResult(

    @SerialName("id")
    val id: String,

    @SerialName("primaryImage")
    val image: Image,

    @SerialName("titleText")
    val title: TitleText,

    @SerialName("releaseDate")
    val releaseDate: ReleaseDate,

    @SerialName("ratingsSummary")
    val ratingsSummary: RatingsSummary,

    @SerialName("genres")
    val genres: Genres,

    @SerialName("plot")
    val plot: Plot
)

@Serializable
data class Image(
    val url: String,
)

@Serializable
data class TitleText(
    val text: String
)

@Serializable
data class ReleaseDate(
    val day: Int,
    val month: Int,
    val year: Int
)

@Serializable
data class RatingsSummary(
    @SerialName("aggregateRating")
    val aggregateRating: Double,

    @SerialName("voteCount")
    val voteCount: Int
)

@Serializable
data class Genres(
    @SerialName("genres")
    val genres: List<Genre>
)

@Serializable
data class Genre(
    val text: String,
)

@Serializable
data class Plot(
    @SerialName("plotText")
    val plotText: Markdown,
)

@Serializable
data class Markdown(
    @SerialName("plainText")
    val plainText: String
)

object MovieApi {
    val retrofitService : MovieService by lazy {
        retrofit.create(MovieService::class.java)
    }
}