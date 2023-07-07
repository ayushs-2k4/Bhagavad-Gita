package com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark

import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {
    val allBookmarks: Flow<List<Bookmark>> = bookmarkDao.getAllBookmarks()

    suspend fun addBookmark(name: String) {
        bookmarkDao.insertBookmark(name = name)
    }

    suspend fun removeBookmark(name: String) {
        bookmarkDao.deleteBookmark(name = name)
    }
}