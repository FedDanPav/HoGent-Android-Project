package com.androidproject.data

import android.content.Context
import com.androidproject.data.local.LocalDatabase
import com.androidproject.data.remote.ApiGenreRepository
import com.androidproject.data.remote.ApiMovieRepository
import com.androidproject.data.remote.GenreRepository
import com.androidproject.data.remote.MovieRepository
import com.androidproject.data.remote.TheMovieDBApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

interface AppModule {
    val apiGenreRepository: GenreRepository
    val apiMovieRepository: MovieRepository
}

class DefaultAppModule(context: Context) : AppModule {
    private fun provideMovieApi(): TheMovieDBApi {
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

    private val database by lazy { LocalDatabase.getDatabase(context) }

    private val movieDao by lazy { database.movieDao() }
    private val genreDao by lazy { database.genreDao() }
    private val movieToGenreDao by lazy { database.movieToGenreDao() }

    private val theMovieDBApi: TheMovieDBApi = provideMovieApi()

    override val apiGenreRepository: GenreRepository by lazy {
        ApiGenreRepository(theMovieDBApi, genreDao)
    }
    override val apiMovieRepository: MovieRepository by lazy {
        ApiMovieRepository(theMovieDBApi, movieDao, movieToGenreDao)
    }
}
