package com.ayushsinghal.bhagvadgita.navigation

sealed class Screen(val route: String) {
    object AllChaptersScreen : Screen("all_chapters_screen")

    object ChapterInformationScreen : Screen("chapter_information_screen")

    object SlokScreen : Screen("slok_screen")
}