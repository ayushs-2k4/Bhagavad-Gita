package com.ayushsinghal.bhagvadgita.features.slok.presentation.random_slok

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RandomSlokScreen(
    randomSlokViewModel: RandomSlokViewModel = hiltViewModel()
) {

    val slok = randomSlokViewModel.slok

    Column {
        slok.value?.let { Text(text = it.slok) }
    }
}