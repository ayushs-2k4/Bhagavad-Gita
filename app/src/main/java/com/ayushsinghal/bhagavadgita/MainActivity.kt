package com.ayushsinghal.bhagavadgita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.AllChaptersScreenViewModel
import com.ayushsinghal.bhagavadgita.navigation.Navigation
import com.ayushsinghal.bhagavadgita.ui.theme.BhagavadGitaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val allChaptersScreenViewModel: AllChaptersScreenViewModel by viewModels()

        installSplashScreen().apply {
            val startTime = System.currentTimeMillis()
            setKeepOnScreenCondition {
                val condition1 =
                    !allChaptersScreenViewModel.hasErrorInRetrieving.value && allChaptersScreenViewModel.isInternetConnected.value && allChaptersScreenViewModel.allChaptersList.value.isEmpty()

                val condition2 =
                    System.currentTimeMillis() - startTime <= 1000 // Splash screen will be shown for maximum of 1000 milliSeconds

                condition1 && condition2
            }
        }



        setContent {
            BhagavadGitaTheme {
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