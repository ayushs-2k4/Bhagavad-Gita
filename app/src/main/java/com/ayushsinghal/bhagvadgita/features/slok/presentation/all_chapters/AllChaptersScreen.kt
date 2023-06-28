package com.ayushsinghal.bhagvadgita.features.slok.presentation.all_chapters

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

val TAG = "myRecognisingTAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllChaptersScreen(
    allChaptersScreenViewModel: AllChaptersScreenViewModel = hiltViewModel()
) {

    var isEnglishSelected by remember {
        mutableStateOf(false)
    }

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

    Scaffold(
        topBar = {
            TopBar(
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
                    onClick = {
                        Log.d(TAG, "Clicked on chapter: $it")
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isEnglishSelected: Boolean,
    onLanguageChangeButtonClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = if (isEnglishSelected) "Chapters" else "अध्याय")
        },
        actions = {
            TextButton(onClick = { onLanguageChangeButtonClicked() }) {
                Text(text = if (isEnglishSelected) "Change to Hindi" else "Change to English")
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
            Text(text = "Verses - ${chapterInfo.versesCount}")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = if (isEnglishSelected) chapterInfo.englishSummary else chapterInfo.hindiSummary,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

data class ChapterInfo(
    val chapterNumber: Int,
    val chapterName: String,
    val translation: String,
    val versesCount: Int,
    val hindiMeaning: String,
    val englishMeaning: String,
    val hindiSummary: String,
    val englishSummary: String
)

//@Preview(showSystemUi = true)
//@Composable
//fun ChapterInfoCardPreview() {
//    ChapterInfoCard(
//        shouldShowInEnglish = true,
//        chapterInfo = ChapterInfo(
//            chapterNumber = 1,
//            chapterName = "अर्जुनविषादयोग",
//            translation = "Arjuna Visada Yoga",
//            versesCount = 47,
//            hindiMeaning = "अर्जुन विषाद योग",
//            englishMeaning = "Arjuna's Dilemma",
//            hindiSummary = "भगवद गीता का पहला अध्याय अर्जुन विशाद योग उन पात्रों और परिस्थितियों का परिचय कराता है जिनके कारण पांडवों और कौरवों के बीच महाभारत का महासंग्राम हुआ। यह अध्याय उन कारणों का वर्णन करता है जिनके कारण भगवद गीता का ईश्वरावेश हुआ। जब महाबली योद्धा अर्जुन दोनों पक्षों पर युद्ध के लिए तैयार खड़े योद्धाओं को देखते हैं तो वह अपने ही रिश्तेदारों एवं मित्रों को खोने के डर तथा फलस्वरूप पापों के कारण दुखी और उदास हो जाते हैं। इसलिए वह श्री कृष्ण को पूरी तरह से आत्मसमर्पण करते हैं। इस प्रकार, भगवद गीता के ज्ञान का प्रकाश होता है।",
//            englishSummary = "The first chapter of the Bhagavad Gita - Arjuna Vishada Yoga introduces the setup, the setting, the characters and the circumstances that led to the epic battle of Mahabharata, fought between the Pandavas and the Kauravas. It outlines the reasons that led to the revelation of the of Bhagavad Gita.\\nAs both armies stand ready for the battle, the mighty warrior Arjuna, on observing the warriors on both sides becomes increasingly sad and depressed due to the fear of losing his relatives and friends and the consequent sins attributed to killing his own relatives. So, he surrenders to Lord Krishna, seeking a solution. Thus, follows the wisdom of the Bhagavad Gita."
//        ),
//        onClick = {}
//    )
//}