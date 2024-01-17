package g58008.mobg5.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL =
    "https://dnsrivnxleeqdtbyhftv.supabase.co"

private const val API_KEY = ""

private var json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface AuthService {
    @Headers(
        "Content-Type: application/json",
        "apikey: $API_KEY"
    )
    @POST("auth/v1/token?grant_type=password")
    suspend fun findRecord(
        @Body authBody: AuthBody
    ) : Response<AuthResponse>
}

@Serializable
data class AuthBody(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("token_type")
    val tokenType: String,
)

object AuthApi {
    val retrofitService : AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}