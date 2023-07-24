package com.ayushsinghal.bhagavadgita.features.slok.presentation.slok

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.common.common_screens.LoadingScreen
import com.ayushsinghal.bhagavadgita.common.common_screens.NoInternetScreen
import com.ayushsinghal.bhagavadgita.features.slok.presentation.bookmark.BookmarkViewModel
import com.ayushsinghal.bhagavadgita.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlokScreen(
    navController: NavController,
    slokViewModel: SlokViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current

    val slok = remember { slokViewModel.slok }

    val context = LocalContext.current

    var isEnglishSelected by rememberSaveable {
        mutableStateOf(false)
    }

    val totalSlokCountInCurrentChapter =
        slokViewModel.totalSlokCountInCurrentChapter.collectAsStateWithLifecycle()

    val isInternetConnected = slokViewModel.isInternetConnected.collectAsStateWithLifecycle()

    val shouldShowNavigationButtons =
        slokViewModel.shouldShowNavigationButtons.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return super.onPreScroll(available, source)
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    if (isInternetConnected.value) {
        if (slok.value == null) {
            LoadingScreen()
        } else {
            val isBookmarked =
                bookmarkViewModel.isPageBookmarked(name = "${slok.value?.chapter!!}" + " " + "${slok.value?.verse!!}")

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(
                        scrollBehavior = scrollBehavior,
                        title = if (isEnglishSelected) "Chapter ${slok.value!!.chapter} - Verse ${slok.value!!.verse}" else "अध्याय ${slok.value!!.chapter} - श्लोक ${slok.value!!.verse}",
                        isBookmarked = isBookmarked,
                        titleStyle = TextStyle(
                            fontSize = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                                if (isEnglishSelected) 17.sp else 18.sp
                            } else {
                                20.sp
                            }
                        ),
                        onClickBookmarkButton = {
                            if (isBookmarked) {
                                bookmarkViewModel.removeBookmark(name = "${slok.value?.chapter!!}" + " " + "${slok.value?.verse!!}")
                            } else {
                                bookmarkViewModel.addBookmark(name = "${slok.value?.chapter!!}" + " " + "${slok.value?.verse!!}")
                            }
                        },
                        onBackClick = { navController.navigateUp() },
                        onClickCopyButton = {
                            slokViewModel.copyToClipboard(
                                context = context,
                                content = if (isEnglishSelected) "Chapter ${slok.value!!.chapter} - Verse ${slok.value!!.verse}\nVerse\n${slok.value!!.slok}\n\nTranslation\n${slok.value!!.siva.et}\n\nExplanation (by Acharya Raman)\n${slok.value!!.raman.et}" else "अध्याय ${slok.value!!.chapter} - श्लोक ${slok.value!!.verse}\nश्लोक\n${slok.value!!.slok}\n\nअनुवाद\n${slok.value!!.rams.ht}\n\nआचार्य राम की व्याख्या\n${slok.value!!.rams.hc}"
                            )
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Slok copied to clipboard",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        onClickShareButton = {
                            slokViewModel.shareSlok(
                                context = context,
                                content = if (isEnglishSelected) "Chapter ${slok.value!!.chapter} - Verse ${slok.value!!.verse}\nVerse\n${slok.value!!.slok}\n\nTranslation\n${slok.value!!.siva.et}\n\nExplanation (by Acharya Raman)\n${slok.value!!.raman.et}" else "अध्याय ${slok.value!!.chapter} - श्लोक ${slok.value!!.verse}\nश्लोक\n${slok.value!!.slok}\n\nअनुवाद\n${slok.value!!.rams.ht}\n\nआचार्य राम की व्याख्या\n${slok.value!!.rams.hc}"
                            )

                        },
                        onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
                    )
                }
            ) { paddingValues ->

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding())
                            .padding(horizontal = 20.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextHeadingDesign(
                            modifier = Modifier.padding(vertical = 20.dp),
                            text = if (isEnglishSelected) "Verse" else "श्लोक",
                            leftImage = R.drawable.left_design,
                            rightImage = R.drawable.right_design
                        )

                        SelectionContainer {
                            Text(text = slok.value!!.slok)
                        }

                        Spacer(modifier = Modifier.height(50.dp))

                        TextHeadingDesign(
                            modifier = Modifier.padding(vertical = 20.dp),
                            text = if (isEnglishSelected) "Translation" else "अनुवाद",
                            leftImage = R.drawable.left_design,
                            rightImage = R.drawable.right_design
                        )
                        SelectionContainer {
                            Text(text = if (isEnglishSelected) slok.value!!.siva.et else slok.value!!.rams.ht)
                        }

                        Spacer(modifier = Modifier.height(50.dp))

                        TextHeadingDesign(
                            modifier = Modifier.padding(vertical = 20.dp),
                            text = if (isEnglishSelected) "Explanation\n(by Acharya Raman)" else "आचार्य राम की व्याख्या",
                            leftImage = R.drawable.left_design,
                            rightImage = R.drawable.right_design
                        )

                        SelectionContainer {
                            Text(
                                text = if (isEnglishSelected) slok.value!!.raman.et else slok.value!!.rams.hc,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }

//                        if (shouldShowNavigationButtons.value) {
//                            BottomNavigationRow(
//                                paddingValues = paddingValues,
//                                onClickPreviousSlokButton = {
//                                    if (slok.value!!.verse != 1) {
//                                        navController.navigateUp()
//                                        navController.navigate(Screen.SlokScreen.route + "?chapter_number=${slok.value!!.chapter}&verse_number=${slok.value!!.verse - 1}&total_slok_count_in_current_chapter=${totalSlokCountInCurrentChapter.value}&should_show_navigation_buttons=${true}")
//                                    } else {
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar(
//                                                message = if (isEnglishSelected) "This is first slok of this chapter" else "यह इस अध्याय का पहला श्लोक है",
//                                                duration = SnackbarDuration.Short
//                                            )
//                                        }
//                                    }
//                                },
//                                onClickNextSlokButton = {
//                                    if (slok.value!!.verse != totalSlokCountInCurrentChapter.value) {
//                                        navController.navigateUp()
//                                        navController.navigate(Screen.SlokScreen.route + "?chapter_number=${slok.value!!.chapter}&verse_number=${slok.value!!.verse + 1}&total_slok_count_in_current_chapter=${totalSlokCountInCurrentChapter.value}&should_show_navigation_buttons=${true}")
//                                    } else {
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar(
//                                                message = if (isEnglishSelected) "This is last slok of this chapter" else "यह इस अध्याय का अंतिम श्लोक है",
//                                                duration = SnackbarDuration.Short
//                                            )
//                                        }
//                                    }
//                                }
//                            )
//                        }
                    }

                    if (shouldShowNavigationButtons.value) {
                        BottomNavigationRow(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 10.dp),
                            paddingValues = paddingValues,
                            onClickPreviousSlokButton = {
                                if (slok.value!!.verse != 1) {
                                    navController.navigateUp()
                                    navController.navigate(Screen.SlokScreen.route + "?chapter_number=${slok.value!!.chapter}&verse_number=${slok.value!!.verse - 1}&total_slok_count_in_current_chapter=${totalSlokCountInCurrentChapter.value}&should_show_navigation_buttons=${true}")
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = if (isEnglishSelected) "This is first slok of this chapter" else "यह इस अध्याय का पहला श्लोक है",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            },
                            onClickNextSlokButton = {
                                if (slok.value!!.verse != totalSlokCountInCurrentChapter.value) {
                                    navController.navigateUp()
                                    navController.navigate(Screen.SlokScreen.route + "?chapter_number=${slok.value!!.chapter}&verse_number=${slok.value!!.verse + 1}&total_slok_count_in_current_chapter=${totalSlokCountInCurrentChapter.value}&should_show_navigation_buttons=${true}")
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = if (isEnglishSelected) "This is last slok of this chapter" else "यह इस अध्याय का अंतिम श्लोक है",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        )

                    }
                }
            }
        }
    } else {
        NoInternetScreen(onClickTryAgain = { slokViewModel.checkInternetAndGetData() })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    isBookmarked: Boolean,
    titleStyle: TextStyle,
    onClickBookmarkButton: () -> Unit,
    onBackClick: () -> Unit,
    onClickCopyButton: () -> Unit,
    onClickShareButton: () -> Unit,
    onLanguageChangeButtonClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = titleStyle
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
            }
        },
        actions = {

            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    if (isSystemInDarkTheme()) {
                        R.raw.bookmark_light_color_animation
                    } else {
                        R.raw.bookmark_dark_color_animation
                    }
                )
            )

            val progress by animateFloatAsState(
                targetValue = if (isBookmarked) 1f else 0f,
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
                label = ""
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(45.dp)
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 40.dp / 2
                        ),
                        onClick = {
                            onClickBookmarkButton()
                        }
                    )
            )

            IconButton(onClick = {
                onClickCopyButton()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.content_copy),
                    contentDescription = "Copy to Clipboard"
                )
            }

            IconButton(onClick = { onClickShareButton() }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
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

@Composable
fun TextHeadingDesign(
    modifier: Modifier = Modifier,
    text: String,
    leftImage: Int,
    rightImage: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(leftImage),
            contentDescription = "left design",
            modifier = Modifier.weight(1f)
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = text,
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(rightImage),
            contentDescription = "right design",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomNavigationRow(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onClickPreviousSlokButton: () -> Unit,
    onClickNextSlokButton: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FloatingActionButton(
            onClick = {
                onClickPreviousSlokButton()
            },
            elevation = FloatingActionButtonDefaults.elevation(
                pressedElevation = 0.dp,
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_ios),
                contentDescription = "Go to previous Slok"
            )
        }

        FloatingActionButton(
            onClick = {
                onClickNextSlokButton()
            },
            elevation = FloatingActionButtonDefaults.elevation(
                pressedElevation = 0.dp,
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_forward_ios),
                contentDescription = "Go to previous Slok"
            )
        }
    }
}