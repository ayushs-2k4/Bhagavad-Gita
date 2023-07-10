package com.ayushsinghal.bhagavadgita.features.slok.presentation.chapter_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagavadGitaRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChapterInformationViewModel @Inject constructor(
    app: Application,
    private val bhagavadGitaRepositoryImpl: BhagavadGitaRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(app) {

    private val _chapterNumber = MutableStateFlow(-1)
    val chapterNumber = _chapterNumber.asStateFlow()

    private val _chapterNameHindi = MutableStateFlow("")
    val chapterNameHindi = _chapterNameHindi.asStateFlow()

    private val _chapterNameEnglish = MutableStateFlow("")
    val chapterNameEnglish = _chapterNameEnglish.asStateFlow()

    private val _verseCount = MutableStateFlow(-1)
    val verseCount = _verseCount.asStateFlow()

    init {
        savedStateHandle.get<Int>("chapter_number")?.let { _chapterNumber.value = it }

        savedStateHandle.get<Int>("verse_count")?.let { _verseCount.value = it }

        savedStateHandle.get<String>("chapter_name_hindi")?.let { _chapterNameHindi.value = it }

        savedStateHandle.get<String>("chapter_name_english")?.let { _chapterNameEnglish.value = it }
    }
}