package com.ayushsinghal.bhagvadgita.features.slok.presentation.chapter_info

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.chapter.ChapterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterInformationViewModel @Inject constructor(
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _chapterNumber = mutableStateOf<Int>(-1)

    private val _chapterModel = mutableStateOf<ChapterModel?>(null)
    val chapterModel: State<ChapterModel?> = _chapterModel


    init {
        savedStateHandle.get<Int>("chapter_number")?.let {
            _chapterNumber.value = it
        }

        getChapterInformation()
    }

    fun getChapterInformation() {
        viewModelScope.launch {
            val response = bhagvadGitaRepositoryImpl.getChapterInformation(_chapterNumber.value)

            val chapterInformationBody = response.body()

            if (response.isSuccessful && chapterInformationBody != null) {
                _chapterModel.value = chapterInformationBody
            }
        }
    }
}