package com.ayushsinghal.bhagavadgita.features.slok.presentation.bookmark

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.Bookmark
import com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository) :
    ViewModel() {

    private val _bookmarks = mutableStateListOf<Bookmark>()
    val bookmarks: List<Bookmark> get() = _bookmarks

    init {
        viewModelScope.launch {
            bookmarkRepository.allBookmarks.collect { savedBookmarks ->
                _bookmarks.clear()
                _bookmarks.addAll(savedBookmarks)
            }
        }
    }

    fun addBookmark(name: String) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(name = name)
        }
    }

    fun removeBookmark(name: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(name = name)
        }
    }

    fun isPageBookmarked(name: String): Boolean {
        return _bookmarks.any {
            it.name == name
        }
    }
}