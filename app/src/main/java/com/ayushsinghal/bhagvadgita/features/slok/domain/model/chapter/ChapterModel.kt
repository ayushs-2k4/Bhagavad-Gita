package com.ayushsinghal.bhagvadgita.features.slok.domain.model.chapter

data class ChapterModel(
    val chapter_number: Int,
    val meaning: MeaningModel,
    val name: String,
    val summary: SummaryModel,
    val translation: String,
    val transliteration: String,
    val verses_count: Int
)