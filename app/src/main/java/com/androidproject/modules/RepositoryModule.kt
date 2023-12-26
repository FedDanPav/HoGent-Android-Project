package com.androidproject.modules

import com.androidproject.data.remote.GenreRepository
import com.androidproject.data.remote.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepository: MovieRepository
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindGenreRepository(
        genreRepository: GenreRepository
    ): GenreRepository
}
