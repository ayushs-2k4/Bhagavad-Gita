package com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("INSERT INTO bookmarks (name) VALUES (:name)")
    suspend fun insertBookmark(name: String)

    @Query("DELETE FROM bookmarks WHERE name = :name")
    suspend fun deleteBookmark(name: String)

    @Query("DELETE FROM bookmarks")
    suspend fun deleteAllBookmarks()
}