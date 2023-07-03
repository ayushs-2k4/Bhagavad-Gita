package com.ayushsinghal.bhagavadgita.features.slok.presentation.slok

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ayushsinghal.bhagavadgita.R
import com.ayushsinghal.bhagavadgita.common.common_screens.LoadingScreen
import com.ayushsinghal.bhagavadgita.common.common_screens.NoInternetScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlokScreen(
    navController: NavController,
    slokViewModel: SlokViewModel = hiltViewModel()
) {

    val slok = remember { slokViewModel.slok }

    val context = LocalContext.current

    var isEnglishSelected by rememberSaveable {
        mutableStateOf(false)
    }

    val isInternetConnected = slokViewModel.isInternetConnected.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()



    if (isInternetConnected.value) {
        if (slok.value == null) {
            LoadingScreen()
        } else {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(
                        scrollBehavior = scrollBehavior,
                        title = if (isEnglishSelected) "Chapter ${slok.value!!.chapter} - Verse ${slok.value!!.verse}" else "अध्याय ${slok.value!!.chapter} - श्लोक ${slok.value!!.verse}",
                        onBackClick = { navController.navigateUp() },
                        onClickShareButton = {
                            slokViewModel.shareSlok(
                                context = context,
                                content = if (isEnglishSelected) "Chapter ${slok.value!!.chapter} - Verse ${slok.value!!.verse}\nVerse\n${slok.value!!.slok}\n\nTranslation\n${slok.value!!.siva.et}\n\nExplanation\n${slok.value!!.raman.et}" else "अध्याय ${slok.value!!.chapter} - श्लोक ${slok.value!!.verse}\nश्लोक\n${slok.value!!.slok}\n\nअनुवाद\n${slok.value!!.rams.ht}\n\nव्याख्या\n${slok.value!!.rams.hc}"
                            )
                        },
                        onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
                    )
                }
            ) { paddingValues ->
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

                    Text(text = slok.value!!.slok)

                    Spacer(modifier = Modifier.height(50.dp))

                    TextHeadingDesign(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = if (isEnglishSelected) "Translation" else "अनुवाद",
                        leftImage = R.drawable.left_design,
                        rightImage = R.drawable.right_design
                    )

                    Text(text = if (isEnglishSelected) slok.value!!.siva.et else slok.value!!.rams.ht)

                    Spacer(modifier = Modifier.height(50.dp))

                    TextHeadingDesign(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = if (isEnglishSelected) "Explanation" else "व्याख्या",
                        leftImage = R.drawable.left_design,
                        rightImage = R.drawable.right_design
                    )

                    Text(
                        text = if (isEnglishSelected) slok.value!!.raman.et else slok.value!!.rams.hc,
                        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                    )

                }
            }
        }
    } else {
        NoInternetScreen(onClickTryAgain = { slokViewModel.checkInternetAndGetData() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onBackClick: () -> Unit,
    onClickShareButton: () -> Unit,
    onLanguageChangeButtonClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
            }
        },
        actions = {

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
            text = text,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(rightImage),
            contentDescription = "right design",
            modifier = Modifier.weight(1f)
        )
    }
}