package com.ayushsinghal.bhagavadgita.features.slok.mapper

import com.ayushsinghal.bhagavadgita.features.slok.data.remote.dto.chapter_dto.Meaning
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.dto.chapter_dto.Summary
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.chapter.MeaningModel
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.chapter.SummaryModel


object ChapterMappers {

    fun meaningDTOToMeaningModelMapper(meaningDTO: Meaning): MeaningModel {
        return MeaningModel(
            en = meaningDTO.en,
            hi = meaningDTO.hi
        )
    }

    fun summaryDTOToSummaryModelMapper(summaryDTO: Summary): SummaryModel {
        return SummaryModel(
            en = summaryDTO.en,
            hi = summaryDTO.hi
        )
    }
}