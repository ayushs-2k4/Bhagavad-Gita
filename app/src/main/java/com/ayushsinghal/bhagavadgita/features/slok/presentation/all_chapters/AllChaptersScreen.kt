package com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.features.slok.domain.ChapterInfo
import com.ayushsinghal.bhagavadgita.navigation.Screen

val TAG = "myRecognisingTAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllChaptersScreen(
    navController: NavController,
    allChaptersScreenViewModel: AllChaptersScreenViewModel = hiltViewModel()
) {

    var isEnglishSelected by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val allChaptersList = allChaptersScreenViewModel.allChaptersList

    val chapterInfoCardList = allChaptersList.value.map {
        ChapterInfo(
            chapterNumber = it.chapter_number,
            chapterName = it.name,
            translation = it.translation,
            versesCount = it.verses_count,
            hindiMeaning = it.meaning.hi,
            englishMeaning = it.meaning.en,
            hindiSummary = it.summary.hi,
            englishSummary = it.summary.en
        )
    }

    if (chapterInfoCardList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
            LottieAnimation(composition = composition, iterations = Int.MAX_VALUE)
        }
    } else {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    scrollBehavior = scrollBehavior,
                    isEnglishSelected = isEnglishSelected,
                    onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(chapterInfoCardList)
                {
                    ChapterInfoCard(
                        isEnglishSelected = isEnglishSelected,
                        chapterInfo = it,
                        onClick = { chapterNumber ->
//                        Log.d(TAG, "Clicked on chapter: ${chapterNumber - 1}")
                            navController.navigate(
                                route = Screen.ChapterInformationScreen.route + "?chapter_number=${chapterInfoCardList[chapterNumber - 1].chapterNumber}"
                            )
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isEnglishSelected: Boolean,
    onLanguageChangeButtonClicked: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = if (isEnglishSelected) "Chapters" else "अध्यायों की सूची")
        },
        actions = {
            IconButton(onClick = { onLanguageChangeButtonClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.translate),
                    contentDescription = "Change Language",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChapterInfoCard(
    modifier: Modifier = Modifier,
    isEnglishSelected: Boolean,
    chapterInfo: ChapterInfo,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.medium)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onClick(chapterInfo.chapterNumber) },
            )
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = chapterInfo.chapterNumber.toString(),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(text = "-")

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = if (isEnglishSelected) chapterInfo.translation else chapterInfo.chapterName,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if (isEnglishSelected) "Verses - ${chapterInfo.versesCount}" else "श्लोक - ${chapterInfo.versesCount}")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = if (isEnglishSelected) chapterInfo.englishSummary else chapterInfo.hindiSummary,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}