package com.ayushsinghal.bhagavadgita.features.slok.presentation.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberDismissState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.navigation.Screen

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun BookmarkScreen(
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarks = bookmarkViewModel.bookmarks

    var isEnglishSelected by rememberSaveable { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                Button(onClick = {
                    bookmarkViewModel.removeAllBookmarks()
                    showDeleteDialog = false
                }) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Delete All Bookmarks?") },
            text = { Text(text = "All bookmarks will be permanently deleted.") }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                showDeleteButton = bookmarks.isNotEmpty(),
                onBackButtonPressed = { navController.navigateUp() },
                onDeleteAllBookmarksButtonPressed = { showDeleteDialog = true },
                onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
            )
        }
    ) { paddingValues ->
        if (bookmarks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bookmark_empty),
                    contentDescription = "Bookmarks",
                    modifier = Modifier.size(100.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "No saved Slok", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Sloks you save will be stored here")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(horizontal = 10.dp)
            ) {
                itemsIndexed(bookmarks, key = { _, bookmark ->
                    bookmark.name
                }) { index, bookmark ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                bookmarkViewModel.removeBookmark(bookmark.name)
                            }

                            if (it == DismissValue.DismissedToEnd) {
                                bookmarkViewModel.removeBookmark(bookmark.name)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        modifier = Modifier.animateItemPlacement(),
                        state = dismissState,
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .clip(MaterialTheme.shapes.extraLarge)
                            ) {
                                val threshold = 0.02f

                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(
                                        if (isSystemInDarkTheme()) {
                                            R.raw.bookmark_light_color_animation
                                        } else {
                                            R.raw.bookmark_dark_color_animation
                                        }
                                    )
                                )

                                val isPlaying by remember {
                                    derivedStateOf {
                                        dismissState.progress.fraction >= threshold
                                    }
                                }

                                LottieAnimation(
                                    isPlaying = isPlaying,
                                    iterations = 1,
                                    speed = -1.5f,
                                    composition = composition,
                                    modifier = Modifier
                                        .align(if (dismissState.dismissDirection == DismissDirection.EndToStart) Alignment.CenterEnd else Alignment.CenterStart)
                                        .size(50.dp),
                                )
                            }
                        },
                        dismissContent = {
                            val chapterNumber = bookmark.name.substringBefore(" ").toInt()
                            val slokNumber = bookmark.name.substringAfter(" ").toInt()
                            OneItem(
                                modifier = if (index == bookmarks.size - 1) {
                                    Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                                } else {
                                    Modifier
                                },
                                chapterNumber = chapterNumber,
                                slokNumber = slokNumber,
                                isEnglishSelected = isEnglishSelected,
                                onClick = { selectedVerseNumber ->
                                    navController.navigate(Screen.SlokScreen.route + "?chapter_number=${chapterNumber}&verse_number=${selectedVerseNumber}&total_slok_count_in_current_chapter=${5}&should_show_navigation_buttons=${false}")
                                }
                            )
                        },
                        directions = setOf(
                            DismissDirection.EndToStart,
                            DismissDirection.StartToEnd
                        )
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    showDeleteButton: Boolean,
    onBackButtonPressed: () -> Unit,
    onDeleteAllBookmarksButtonPressed: () -> Unit,
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
            if (showDeleteButton) {
                IconButton(onClick = { onDeleteAllBookmarksButtonPressed() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "Delete All Bookmarks",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

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
//    BookmarkScreen(
//        navController = NavController(LocalContext.current)
//    )
//}

@Preview
@Composable
fun OneItemPreview() {
    OneItem(
        chapterNumber = 1,
        isEnglishSelected = true,
        onClick = {},
        slokNumber = 2
    )
}