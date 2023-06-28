package com.ayushsinghal.bhagavadgita.features.slok.di

import com.ayushsinghal.bhagavadgita.features.slok.data.remote.BhagvadGitaApi
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBhagvadGitaApi(): BhagvadGitaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BhagvadGitaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBhagvadGitaRepositoryImpl(bhagvadGitaApi: BhagvadGitaApi): BhagvadGitaRepositoryImpl {
        return BhagvadGitaRepositoryImpl(bhagvadGitaApi = bhagvadGitaApi)
    }
}