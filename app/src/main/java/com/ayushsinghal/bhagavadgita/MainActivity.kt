package com.ayushsinghal.bhagavadgita

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayushsinghal.bhagavadgita.common.common_screens.LoadingScreen
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.AllChaptersScreenViewModel
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.TAG
import com.ayushsinghal.bhagavadgita.features.slok.presentation.chapter_info.OneItem
import com.ayushsinghal.bhagavadgita.navigation.Navigation
import com.ayushsinghal.bhagavadgita.navigation.Screen
import com.ayushsinghal.bhagavadgita.ui.theme.BhagvadGitaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val allChaptersScreenViewModel: AllChaptersScreenViewModel by viewModels()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !allChaptersScreenViewModel.hasErrorInRetrieving.value && allChaptersScreenViewModel.isInternetConnected.value && allChaptersScreenViewModel.allChaptersList.value.isEmpty()
            }
        }



        setContent {
            BhagvadGitaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}