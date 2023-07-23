package com.ayushsinghal.bhagavadgita.features.slok.presentation.bookmark

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.Bookmark
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.BookmarkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val bookmarkRepositoryImpl: BookmarkRepositoryImpl) :
    ViewModel() {

    private val _bookmarks = mutableStateListOf<Bookmark>()
    val bookmarks: List<Bookmark> get() = _bookmarks

    private var lastDeletedBookmarks: MutableList<String> = emptyList<String>().toMutableList()

    init {
        getAllBookmarks()
    }

    private fun getAllBookmarks() {
        viewModelScope.launch {
            bookmarkRepositoryImpl.allBookmarks.collect { savedBookmarks ->
                _bookmarks.clear()
                _bookmarks.addAll(savedBookmarks.reversed())
            }
        }
    }

    fun addBookmark(name: String) {
        viewModelScope.launch {
            bookmarkRepositoryImpl.addBookmark(name = name)
        }
    }

    fun removeBookmark(name: String) {
        lastDeletedBookmarks.clear()
        lastDeletedBookmarks.add(name)
        viewModelScope.launch {
            bookmarkRepositoryImpl.removeBookmark(name = name)
        }
        getAllBookmarks()
    }

    fun removeAllBookmarks() {
        lastDeletedBookmarks.clear()
        _bookmarks.reversed().forEach {
            lastDeletedBookmarks.add(it.name)
        }
        viewModelScope.launch {
            bookmarkRepositoryImpl.removeAllBookmarks()
        }
        getAllBookmarks()
    }

    fun isPageBookmarked(name: String): Boolean {
        return _bookmarks.any {
            it.name == name
        }
    }

    fun undoDeleteBookmark() {
        lastDeletedBookmarks.forEach {
            addBookmark(it)
        }
        lastDeletedBookmarks.clear()
    }
}