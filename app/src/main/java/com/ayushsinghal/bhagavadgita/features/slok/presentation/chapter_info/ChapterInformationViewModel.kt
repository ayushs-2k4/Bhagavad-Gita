package com.ayushsinghal.bhagavadgita.features.slok.presentation.chapter_info

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.chapter.ChapterModel
import com.ayushsinghal.bhagavadgita.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterInformationViewModel @Inject constructor(
    app: Application,
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(app) {

    private val _chapterNumber = mutableStateOf<Int>(-1)
    val chapterNumber: State<Int> = _chapterNumber

    private val _chapterNameHindi = mutableStateOf<String>("")
    val chapterNameHindi: State<String> = _chapterNameHindi

    private val _chapterNameEnglish = mutableStateOf<String>("")
    val chapterNameEnglish: State<String> = _chapterNameEnglish

    private val _verseCount = mutableStateOf<Int>(-1)
    val verseCount: State<Int> = _verseCount

    init {
        savedStateHandle.get<Int>("chapter_number")?.let { _chapterNumber.value = it }

        savedStateHandle.get<Int>("verse_count")?.let { _verseCount.value = it }

        savedStateHandle.get<String>("chapter_name_hindi")?.let { _chapterNameHindi.value = it }

        savedStateHandle.get<String>("chapter_name_english")?.let { _chapterNameEnglish.value = it }
    }
}