package com.ayushsinghal.bhagvadgita.features.slok.presentation.slok

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayushsinghal.bhagvadgita.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlokScreen(
    navController: NavController,
    slokViewModel: SlokViewModel = hiltViewModel()
) {

    val slok = remember { slokViewModel.slok }

    var isEnglishSelected by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (slok.value == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                strokeCap = StrokeCap.Round
            )
        }
    } else {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    scrollBehavior = scrollBehavior,
                    title = "अध्याय ${slok.value!!.chapter} श्लोक ${slok.value!!.verse}",
                    onBackClick = { navController.navigateUp() },
                    onLanguageChangeButtonClicked = { isEnglishSelected = !isEnglishSelected }
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
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

                Text(text = if (isEnglishSelected) slok.value!!.raman.et else slok.value!!.rams.hc)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onBackClick: () -> Unit,
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