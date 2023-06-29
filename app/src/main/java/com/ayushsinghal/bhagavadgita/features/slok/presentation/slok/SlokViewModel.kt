package com.ayushsinghal.bhagavadgita.features.slok.presentation.slok

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.BhagvadGitaApp
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.slok.SlokModel
import com.ayushsinghal.bhagavadgita.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlokViewModel @Inject constructor(
    app: Application,
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(app) {


    private val _chapterNumber = mutableStateOf(1)
    val chapterNumber: State<Int> = _chapterNumber

    private val _verseNumber = mutableStateOf(1)
    val verseNumber: State<Int> = _verseNumber

    private val _isInternetConnected = mutableStateOf<Boolean>(false)
    val isInternetConnected: State<Boolean> = _isInternetConnected

    init {
        savedStateHandle.get<Int>("chapter_number")?.let {
            _chapterNumber.value = it
        }

        savedStateHandle.get<Int>("verse_number")?.let {
            _verseNumber.value = it
        }

        checkInternetAndGetData()
    }

    fun checkInternetAndGetData() {
        if (NetworkUtils.isInternetAvailable(bhagvadGitaApp = getApplication())) {
            _isInternetConnected.value = true
            getRandomSlok()
        } else {
            _isInternetConnected.value = false
        }
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