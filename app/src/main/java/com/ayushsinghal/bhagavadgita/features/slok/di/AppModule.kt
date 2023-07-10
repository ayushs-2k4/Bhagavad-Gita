package com.ayushsinghal.bhagavadgita.features.slok.di

import android.app.Application
import androidx.room.Room
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.BookmarkDao
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.BookmarkDatabase
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.BookmarkRepository
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.Migrations.MIGRATION_0_1
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.BhagavadGitaApi
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagavadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.domain.repository.FakeRepository
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
    fun provideBhagavadGitaApi(): BhagavadGitaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BhagavadGitaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBhagavadGitaRepositoryImpl(bhagavadGitaApi: BhagavadGitaApi): BhagavadGitaRepositoryImpl {
        return BhagavadGitaRepositoryImpl(bhagavadGitaApi = bhagavadGitaApi)
    }

    @Provides
    @Singleton
    fun provideFakeRepository(): FakeRepository {
        return FakeRepository()
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(application: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            application,
            BookmarkDatabase::class.java,
            "bookmark_database"
        )
            .addMigrations(MIGRATION_0_1)
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(bookmarkDatabase: BookmarkDatabase): BookmarkDao {
        return bookmarkDatabase.bookmarkDao()
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao): BookmarkRepository {
        return BookmarkRepository(bookmarkDao)
    }
}