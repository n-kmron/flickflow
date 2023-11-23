package g58008.mobg5.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRuc3Jpdm54bGVlcWR0YnloZnR2Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NzE0MDI2MSwiZXhwIjoyMDEyNzE2MjYxfQ.jgJ49-c9Z8iPQnLVTnPlfRZpKwyBKht-OY8wMTceSiM"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json{
        ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType()))
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
    val access_token: String,
    val token_type: String,
)

object AuthApi {
    val retrofitService : AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}