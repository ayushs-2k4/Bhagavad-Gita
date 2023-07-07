package com.ayushsinghal.bhagavadgita.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.AllChaptersScreen
import com.ayushsinghal.bhagavadgita.features.slok.presentation.bookmark.BookmarkScreen
import com.ayushsinghal.bhagavadgita.features.slok.presentation.chapter_info.ChapterInformationScreen
import com.ayushsinghal.bhagavadgita.features.slok.presentation.slok.SlokScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.AllChaptersScreen.route
    ) {

        composable(route = Screen.AllChaptersScreen.route)
        {
            AllChaptersScreen(navController = navController)
        }

        composable(
            route = Screen.ChapterInformationScreen.route + "?chapter_number={chapter_number}&chapter_name_hindi={chapter_name_hindi}&chapter_name_english={chapter_name_english}&verse_count={verse_count}",
            arguments = listOf(
                navArgument("chapter_number") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("chapter_name_hindi") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("chapter_name_english") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("verse_count") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        )
        {
            ChapterInformationScreen(navController = navController)
        }

        composable(
            route = Screen.SlokScreen.route + "?chapter_number={chapter_number}&verse_number={verse_number}&total_slok_count_in_current_chapter={total_slok_count_in_current_chapter}&should_show_navigation_buttons={should_show_navigation_buttons}",
            arguments = listOf(
                navArgument("chapter_number") {
                    type = NavType.IntType
                },
                navArgument("verse_number") {
                    type = NavType.IntType
                },
                navArgument("total_slok_count_in_current_chapter") {
                    type = NavType.IntType
                },
                navArgument("should_show_navigation_buttons") {
                    type = NavType.BoolType
                    nullable = false
                }
            )
        ) {
            SlokScreen(navController = navController)
        }

        composable(route = Screen.BookmarksScreen.route) {
            BookmarkScreen(navController = navController)
        }
    }

}