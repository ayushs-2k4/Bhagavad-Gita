package com.ayushsinghal.bhagavadgita.features.slok.data.remote.dto.all_chapters_dto

data class AllChaptersListDTOItem(
    val chapter_number: Int,
    val meaning: Meaning,
    val name: String,
    val summary: Summary,
    val translation: String,
    val transliteration: String,
    val verses_count: Int
)