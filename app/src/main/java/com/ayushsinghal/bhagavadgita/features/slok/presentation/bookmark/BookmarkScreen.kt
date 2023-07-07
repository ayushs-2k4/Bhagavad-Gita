package com.ayushsinghal.bhagavadgita.features.slok.presentation.bookmark

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.TAG
import com.ayushsinghal.bhagavadgita.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarks = bookmarkViewModel.bookmarks

    var isEnglishSelected by rememberSaveable { mutableStateOf(false) }


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                onBackButtonPressed = { navController.navigateUp() },
                onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 10.dp)
        ) {
            items(bookmarks.size) {
                val chapterNumber = bookmarks[it].name.substringBefore(" ").toInt()
                val slokNumber = bookmarks[it].name.substringAfter(" ").toInt()
                OneItem(
                    modifier = if (it == bookmarks.size - 1) {
                        Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                    } else {
                        Modifier
                    },
                    chapterNumber = chapterNumber,
                    slokNumber = slokNumber,
                    isEnglishSelected = isEnglishSelected,
                    onClick = { selectedVerseNumber ->
                        Log.d(TAG, "clicked on verse: $selectedVerseNumber")
                        navController.navigate(Screen.SlokScreen.route + "?chapter_number=${chapterNumber}&verse_number=${selectedVerseNumber}&total_slok_count_in_current_chapter=${5}&should_show_navigation_buttons=${false}")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackButtonPressed: () -> Unit,
    onLanguageChangeButtonClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Bookmarks",
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
                    painter = painterResource(id = R.drawable.translate_hindi_english),
                    contentDescription = "Change Language",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OneItem(
    modifier: Modifier = Modifier,
    chapterNumber: Int,
    slokNumber: Int,
    isEnglishSelected: Boolean,
    onClick: (Int) -> Unit
) {
    OutlinedCard(
        modifier = modifier
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
            text = if (isEnglishSelected) "Chapter $chapterNumber, Verse $slokNumber" else "अध्याय $chapterNumber, श्लोक $slokNumber",
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

//@Preview
//@Composable
//fun BookmarkScreenPreview() {
//    BookmarkScreen()
//}