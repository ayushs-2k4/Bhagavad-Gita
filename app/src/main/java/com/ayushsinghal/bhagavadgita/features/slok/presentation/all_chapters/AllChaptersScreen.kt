package com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.common.common_screens.LoadingScreen
import com.ayushsinghal.bhagavadgita.common.common_screens.NoInternetScreen
import com.ayushsinghal.bhagavadgita.common.common_screens.ServerErrorScreen
import com.ayushsinghal.bhagavadgita.features.slok.domain.ChapterInfo
import com.ayushsinghal.bhagavadgita.navigation.Screen

const val TAG = "myRecognisingTAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllChaptersScreen(
    navController: NavController,
    allChaptersScreenViewModel: AllChaptersScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var isEnglishSelected by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val allChaptersList =
        allChaptersScreenViewModel.allChaptersList.collectAsStateWithLifecycle().value

    val isInternetConnected =
        allChaptersScreenViewModel.isInternetConnected.collectAsStateWithLifecycle().value

    val hasErrorInRetrieving =
        allChaptersScreenViewModel.hasErrorInRetrieving.collectAsStateWithLifecycle().value

    if (hasErrorInRetrieving) {
        val errorCode = allChaptersScreenViewModel.responseCode
        ServerErrorScreen(message = "Error Code - $errorCode\n Try again after some time")
    } else {
        val chapterInfoCardList = allChaptersList.map {
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

        if (isInternetConnected) {
            if (chapterInfoCardList.isEmpty()) {
                LoadingScreen()
            } else {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopBar(
                            scrollBehavior = scrollBehavior,
                            isEnglishSelected = isEnglishSelected,
                            onLanguageChangeButtonClicked = {
                                isEnglishSelected = !isEnglishSelected
                            }
                        )
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
                    ) {
                        items(chapterInfoCardList.size) {
                            ChapterInfoCard(
                                modifier = if (it == chapterInfoCardList.size - 1) {
                                    Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                                } else {
                                    Modifier
                                },
                                isEnglishSelected = isEnglishSelected,
                                chapterInfo = chapterInfoCardList[it],
                                onClickCard = { chapterNumber ->
                                    navController.navigate(
                                        route = Screen.ChapterInformationScreen.route + "?chapter_number=${chapterInfoCardList[chapterNumber - 1].chapterNumber}&chapter_name_hindi=${chapterInfoCardList[chapterNumber - 1].chapterName}&chapter_name_english=${chapterInfoCardList[chapterNumber - 1].translation}&verse_count=${chapterInfoCardList[chapterNumber - 1].versesCount}"
                                    )
                                },
                                onClickShareIcon = { chapterNumber ->
                                    allChaptersScreenViewModel.shareChapterInformation(
                                        context = context,
                                        content = if (isEnglishSelected) {
                                            "Chapter ${chapterInfoCardList[chapterNumber - 1].chapterNumber} - ${chapterInfoCardList[chapterNumber - 1].translation}\n${chapterInfoCardList[chapterNumber - 1].englishSummary}"
                                        } else {
                                            "अध्याय ${chapterInfoCardList[chapterNumber - 1].chapterNumber} - ${chapterInfoCardList[chapterNumber - 1].chapterName}\n${chapterInfoCardList[chapterNumber - 1].hindiSummary}"
                                        }
                                    )
                                },
                                onClickCopyIcon = { chapterNumber ->
                                    allChaptersScreenViewModel.copyToClipboard(
                                        context = context,
                                        content = if (isEnglishSelected) {
                                            "Chapter ${chapterInfoCardList[chapterNumber - 1].chapterNumber} - ${chapterInfoCardList[chapterNumber - 1].translation}\n${chapterInfoCardList[chapterNumber - 1].englishSummary}"
                                        } else {
                                            "अध्याय ${chapterInfoCardList[chapterNumber - 1].chapterNumber} - ${chapterInfoCardList[chapterNumber - 1].chapterName}\n${chapterInfoCardList[chapterNumber - 1].hindiSummary}"
                                        }
                                    )
                                }
                            )
                        }

                    }
                }
            }
        } else {
            NoInternetScreen(onClickTryAgain = { allChaptersScreenViewModel.checkInternetAndGetData() })
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
                    painter = painterResource(id = R.drawable.translate_hindi_english),
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
    onClickCard: (Int) -> Unit,
    onClickShareIcon: (Int) -> Unit,
    onClickCopyIcon: (Int) -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.medium)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onClickCard(chapterInfo.chapterNumber) },
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 7.dp)
                .padding(horizontal = 20.dp),
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

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(

                onClick = { onClickCopyIcon(chapterInfo.chapterNumber) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.content_copy),
                    contentDescription = "Copy to Clipboard"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            FloatingActionButton(
                onClick = { onClickShareIcon(chapterInfo.chapterNumber) }
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun ChapterInfoCardPreview() {
//    ChapterInfoCard(
//        isEnglishSelected = false,
//        chapterInfo = ChapterInfo(
//            chapterNumber = 1,
//            chapterName = "Name",
//            translation = "Translation",
//            versesCount = 987,
//            hindiMeaning = "Hindi Meaning",
//            englishMeaning = "English Meaning",
//            hindiSummary = "Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary ",
//            englishSummary = "English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary ",
//        ),
//        onClick = {}
//    )
//}