package com.ayushsinghal.bhagavadgita.features.slok.presentation.slok

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.repository.BhagvadGitaRepositoryImpl
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.slok.SlokModel
import com.ayushsinghal.bhagavadgita.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlokViewModel @Inject constructor(
    app: Application,
    private val bhagvadGitaRepositoryImpl: BhagvadGitaRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(app) {


    private val _chapterNumber = MutableStateFlow(1)
//    val chapterNumber= _chapterNumber.asStateFlow()

    private val _verseNumber = MutableStateFlow(1)
//    val verseNumber= _verseNumber.asStateFlow()

    private val _totalSlokCountInCurrentChapter = MutableStateFlow(1)
    val totalSlokCountInCurrentChapter = _totalSlokCountInCurrentChapter.asStateFlow()

    private val _isInternetConnected = MutableStateFlow(false)
    val isInternetConnected = _isInternetConnected.asStateFlow()

    init {
        savedStateHandle.get<Int>("chapter_number")?.let {
            _chapterNumber.value = it
        }

        savedStateHandle.get<Int>("verse_number")?.let {
            _verseNumber.value = it
        }

        savedStateHandle.get<Int>("total_slok_count_in_current_chapter")?.let {
            _totalSlokCountInCurrentChapter.value = it
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

    fun shareSlok(context: Context, content: String) {
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
        val clip = ClipData.newPlainText("Slok Information", content)
        clipboardManager.setPrimaryClip(clip)
    }
}