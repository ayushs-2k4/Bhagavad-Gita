package com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.chapter_dto

data class ChapterDTO(
    val chapter_number: Int,
    val meaning: Meaning,
    val name: String,
    val summary: Summary,
    val translation: String,
    val transliteration: String,
    val verses_count: Int
)