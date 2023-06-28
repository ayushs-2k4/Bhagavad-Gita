package com.ayushsinghal.bhagvadgita.features.slok.presentation.all_chapters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.all_chapters.AllChaptersListModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllChaptersScreenViewModel @Inject constructor(
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl
) : ViewModel() {

    private val _allChaptersList = mutableStateOf<List<AllChaptersListModelItem>>(emptyList())
    val allChaptersList: State<List<AllChaptersListModelItem>> = _allChaptersList

    init {
        getAllChaptersInfo()
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