package g58008.mobg5.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

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
    @Headers(
        "X-RapidAPI-Key: $API_KEY",
        "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com",
        "Host: moviesdatabase.p.rapidapi.com"
    )

    /**
     * This request require a param "list" which can be:
     * - top_boxoffice_200
     * - titles
     * - and other here : https://rapidapi.com/SAdrian/api/moviesdatabase/details
     */
    @GET("titles/random?list=top_boxoffice_200&limit=1")
    suspend fun getRandomMovie() : Response<MovieResponse>
}

@Serializable
data class MovieResponse(
    @SerialName("results")
    val results: List<MovieResult>
)

@Serializable
data class MovieResult(

    @SerialName("primaryImage")
    val primaryImage: Image,

    @SerialName("titleText")
    val titleText: TitleText,

    @SerialName("releaseDate")
    val releaseDate: ReleaseDate,

    @SerialName("position")
    val position: Int
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

object MovieApi {
    val retrofitService : MovieService by lazy {
        retrofit.create(MovieService::class.java)
    }
}