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
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun provideMovieApi(): TheMovieDBApi {
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(RequestInterceptor)
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

    object RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            println("Outgoing request to ${request.url}")
            return chain.proceed(request)
        }
    }

    private val theMovieDBApi: TheMovieDBApi = provideMovieApi()

    override val apiGenreRepository: GenreRepository by lazy {
        ApiGenreRepository(theMovieDBApi)
    }
    override val apiMovieRepository: MovieRepository by lazy {
        ApiMovieRepository(theMovieDBApi)
    }
}
