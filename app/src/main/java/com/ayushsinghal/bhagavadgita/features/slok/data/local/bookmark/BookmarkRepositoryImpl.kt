package com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark

import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(private val bookmarkDao: BookmarkDao) {
    var allBookmarks: Flow<List<Bookmark>> = bookmarkDao.getAllBookmarks()

    suspend fun addBookmark(name: String) {
        bookmarkDao.insertBookmark(name = name)
    }

    suspend fun removeBookmark(name: String) {
        bookmarkDao.deleteBookmark(name = name)
    }

    suspend fun removeAllBookmarks() {
        bookmarkDao.deleteAllBookmarks()
    }
}