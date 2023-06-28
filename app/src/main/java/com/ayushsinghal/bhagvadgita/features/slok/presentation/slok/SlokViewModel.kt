package com.ayushsinghal.bhagvadgita.features.slok.presentation.slok

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SlokModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlokViewModel @Inject constructor(
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _chapterNumber = mutableStateOf(1)
    val chapterNumber: State<Int> = _chapterNumber

    private val _verseNumber = mutableStateOf(1)
    val verseNumber: State<Int> = _verseNumber

    init {
        savedStateHandle.get<Int>("chapter_number")?.let {
            _chapterNumber.value = it
        }

        savedStateHandle.get<Int>("verse_number")?.let {
            _verseNumber.value = it
        }

        getRandomSlok()
    }

    private val _slok = mutableStateOf<SlokModel?>(null)
    val slok: State<SlokModel?> = _slok

    private fun getRandomSlok() {
        viewModelScope.launch {
            val result = bhagvadGitaRepositoryImpl.getSlok(
                chapter = _chapterNumber.value,
                verse = _verseNumber.value
            )

            if (result.body() != null) {
                val slokInfo = result.body()

                _slok.value = slokInfo
            }
        }
    }
}