package com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters

data class AllChaptersListModelItem(
    val chapter_number: Int,
    val meaning: MeaningModel,
    val name: String,
    val summary: SummaryModel,
    val translation: String,
    val transliteration: String,
    val verses_count: Int
)