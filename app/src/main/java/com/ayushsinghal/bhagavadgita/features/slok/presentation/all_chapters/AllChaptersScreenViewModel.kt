package com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters.AllChaptersListModelItem
import com.ayushsinghal.bhagavadgita.features.slok.domain.repository.FakeRepository
import com.ayushsinghal.bhagavadgita.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllChaptersScreenViewModel @Inject constructor(
    app: Application,
    private val bhagavadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
) : AndroidViewModel(app) {

    private val _allChaptersList = MutableStateFlow<List<AllChaptersListModelItem>>(emptyList())
    val allChaptersList = _allChaptersList.asStateFlow()

    private val _isInternetConnected = MutableStateFlow(false)
    val isInternetConnected = _isInternetConnected.asStateFlow()

    private val _hasErrorInRetrieving = MutableStateFlow(false)
    val hasErrorInRetrieving = _hasErrorInRetrieving.asStateFlow()

    private val _responseCode = MutableStateFlow(-199)
    val responseCode = _responseCode.asStateFlow()

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
            val response = bhagavadGitaRepositoryImpl.getAllChapterInformation()
            val allChaptersInfo = response.body()

            if (response.isSuccessful && allChaptersInfo != null) {
                withContext(Dispatchers.Main) {
                    _hasErrorInRetrieving.value = false
                    _responseCode.value = response.code()

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
            } else {
                withContext(Dispatchers.Main) {
                    _responseCode.value = response.code()
                    _hasErrorInRetrieving.value = true
                }
            }
        }
    }

    fun shareChapterInformation(context: Context, content: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
            val intentChooser = Intent.createChooser(this, "Hi")
            context.startActivity(intentChooser)
        }
    }

    fun copyToClipboard(context: Context, content: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Chapter Information", content)
        clipboardManager.setPrimaryClip(clip)
    }
}