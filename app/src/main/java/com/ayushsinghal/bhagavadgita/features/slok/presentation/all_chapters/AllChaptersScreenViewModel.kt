package com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters.AllChaptersListModelItem
import com.ayushsinghal.bhagavadgita.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllChaptersScreenViewModel @Inject constructor(
    app: Application,
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
) : AndroidViewModel(app) {

    private val _allChaptersList = mutableStateOf<List<AllChaptersListModelItem>>(emptyList())
    val allChaptersList: State<List<AllChaptersListModelItem>> = _allChaptersList

    private val _isInternetConnected = mutableStateOf<Boolean>(false)
    val isInternetConnected: State<Boolean> = _isInternetConnected

    init {
        checkInternetAndGetData()
    }

    fun checkInternetAndGetData() {
        if (NetworkUtils.isInternetAvailable(bhagvadGitaApp = getApplication())) {
            _isInternetConnected.value = true
            getAllChaptersInfo()
        } else {
            _isInternetConnected.value = false
        }
    }

    private fun getAllChaptersInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = bhagvadGitaRepositoryImpl.getAllChapterInformation()
            val allChaptersInfo = response.body()

            if (response.isSuccessful && allChaptersInfo != null) {
                withContext(Dispatchers.Main) {

                    val allChaptersListModelItemList = allChaptersInfo.map {
                        AllChaptersListModelItem(
                            chapter_number = it.chapter_number,
                            meaning = it.meaning,
                            name = it.name,
                            summary = it.summary,
                            transliteration = it.transliteration,
                            translation = it.translation,
                            verses_count = it.verses_count
                        )
                    }

                    _allChaptersList.value = allChaptersListModelItemList
                }
            }
        }
    }
}