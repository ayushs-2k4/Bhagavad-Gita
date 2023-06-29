package com.ayushsinghal.bhagavadgita.features.slok.presentation.chapter_info

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.common.common_screens.NoInternetScreen
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.TAG
import com.ayushsinghal.bhagavadgita.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterInformationScreen(
    navController: NavController,
    chapterInformationViewModel: ChapterInformationViewModel = hiltViewModel()
) {

    val chapterModel = chapterInformationViewModel.chapterModel.value

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val isInternetConnected = chapterInformationViewModel.isInternetConnected

    if (isInternetConnected.value) {
        if (chapterModel == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
                LottieAnimation(composition = composition, iterations = Int.MAX_VALUE)
            }
        } else {

            var isEnglishSelected by rememberSaveable {
                mutableStateOf(false)
            }

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(
                        scrollBehavior = scrollBehavior,
                        chapterName = chapterModel.name,
                        onBackButtonPressed = { navController.navigateUp() },
                        onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 10.dp)
                ) {
                    items(chapterModel.verses_count) {
                        OneItem(
                            slokNumber = it + 1,
                            isEnglishSelected = isEnglishSelected,
                            onClick = { selectedVerseNumber ->
                                Log.d(TAG, "clicked on verse: $selectedVerseNumber")
                                navController.navigate(Screen.SlokScreen.route + "?chapter_number=${chapterModel.chapter_number}&verse_number=${selectedVerseNumber}")
                            }
                        )
                    }
                }
            }
        }
    } else {
        NoInternetScreen(onClickTryAgain = { chapterInformationViewModel.checkInternetAndGetData() })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OneItem(
    slokNumber: Int,
    isEnglishSelected: Boolean,
    onClick: (Int) -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onClick(slokNumber) },
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Text(
            text = if (isEnglishSelected) "Verse $slokNumber" else "श्लोक $slokNumber",
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    chapterName: String,
    onBackButtonPressed: () -> Unit,
    onLanguageChangeButtonClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = chapterName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackButtonPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
            }
        },
        actions = {
            IconButton(onClick = { onLanguageChangeButtonClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.translate),
                    contentDescription = "Change Language",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}