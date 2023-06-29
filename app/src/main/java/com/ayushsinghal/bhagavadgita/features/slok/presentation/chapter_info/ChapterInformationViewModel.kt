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

    private val _chapterModel = mutableStateOf<ChapterModel?>(null)
    val chapterModel: State<ChapterModel?> = _chapterModel

    private val _isInternetConnected = mutableStateOf<Boolean>(false)
    val isInternetConnected: State<Boolean> = _isInternetConnected


    init {
        savedStateHandle.get<Int>("chapter_number")?.let {
            _chapterNumber.value = it
        }

        checkInternetAndGetData()
    }

    fun checkInternetAndGetData() {
        if (NetworkUtils.isInternetAvailable(bhagvadGitaApp = getApplication())) {
            _isInternetConnected.value = true
            getChapterInformation()
        } else {
            _isInternetConnected.value = false
        }
    }

    private fun getChapterInformation() {
        viewModelScope.launch {
            val response = bhagvadGitaRepositoryImpl.getChapterInformation(_chapterNumber.value)

            val chapterInformationBody = response.body()

            if (response.isSuccessful && chapterInformationBody != null) {
                _chapterModel.value = chapterInformationBody
            }
        }
    }
}