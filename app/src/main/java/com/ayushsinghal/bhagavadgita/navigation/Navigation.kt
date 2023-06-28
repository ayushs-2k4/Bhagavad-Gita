package com.ayushsinghal.bhagavadgita.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ayushsinghal.bhagavadgita.features.slok.presentation.all_chapters.AllChaptersScreen
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
            route = Screen.ChapterInformationScreen.route + "?chapter_number={chapter_number}",
            arguments = listOf(
                navArgument("chapter_number") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        )
        {
            ChapterInformationScreen(navController = navController)
        }

        composable(
            route = Screen.SlokScreen.route + "?chapter_number={chapter_number}&verse_number={verse_number}",
            arguments = listOf(
                navArgument("chapter_number") {
                    type = NavType.IntType
                },
                navArgument("verse_number") {
                    type = NavType.IntType
                }
            )
        ) {
            SlokScreen(navController = navController)
        }


    }

}