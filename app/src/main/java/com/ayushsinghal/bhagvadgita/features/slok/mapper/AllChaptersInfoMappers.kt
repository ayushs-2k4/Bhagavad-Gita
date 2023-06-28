package com.ayushsinghal.bhagvadgita.features.slok.mapper

import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.all_chapters_dto.Meaning
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.all_chapters_dto.Summary
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.all_chapters.MeaningModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.all_chapters.SummaryModel

object AllChaptersInfoMappers {

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