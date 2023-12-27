package com.androidproject.data

import android.content.Context
import com.androidproject.BuildConfig
import com.androidproject.data.remote.ApiGenreRepository
import com.androidproject.data.remote.ApiMovieRepository
import com.androidproject.data.remote.GenreRepository
import com.androidproject.data.remote.MovieRepository
import com.androidproject.data.remote.TheMovieDBApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface AppModule {
    val apiGenreRepository: GenreRepository
    val apiMovieRepository: MovieRepository
}

class DefaultAppModule(context: Context) : AppModule {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun provideMovieApi(): TheMovieDBApi {
        val client = OkHttpClient()
            .newBuilder()
            .build()

        return Retrofit.Builder()
            .baseUrl(TheMovieDBApi.BASE_URL)
            .addConverterFactory(
                JacksonConverterFactory.create()
            )
            .client(client)
            .build()
            .create(TheMovieDBApi::class.java)
    }

    private val theMovieDBApi: TheMovieDBApi = provideMovieApi()

    override val apiGenreRepository: GenreRepository by lazy {
        ApiGenreRepository(theMovieDBApi)
    }
    override val apiMovieRepository: MovieRepository by lazy {
        ApiMovieRepository(theMovieDBApi)
    }
}
