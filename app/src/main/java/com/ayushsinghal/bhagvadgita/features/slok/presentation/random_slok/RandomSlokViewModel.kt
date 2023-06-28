package com.ayushsinghal.bhagvadgita.features.slok.presentation.random_slok

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SlokModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomSlokViewModel @Inject constructor(
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl
) : ViewModel() {


    private val _chapter = mutableStateOf<Int>(1)
    val chapter: State<Int> = _chapter

    private val _verse = mutableStateOf<Int>(1)
    val verse: State<Int> = _verse

    init {
        getRandomSlok()
        _chapter.value = 1
        _verse.value = 1
    }

    private val _slok = mutableStateOf<SlokModel?>(null)
    val slok: State<SlokModel?> = _slok

    fun getRandomSlok() {
        viewModelScope.launch {
            val result = bhagvadGitaRepositoryImpl.getSlok(
                chapter = _chapter.value,
                verse = _verse.value
            )

            if (result.body() != null) {
                val slokInfo = result.body()

                _slok.value = slokInfo
            }
        }
    }
}