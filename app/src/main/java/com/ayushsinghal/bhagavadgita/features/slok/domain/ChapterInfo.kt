package com.ayushsinghal.bhagavadgita.features.slok.domain

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