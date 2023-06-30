package com.ayushsinghal.bhagavadgita.features.slok.domain.repository

import com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters.AllChaptersListModel
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters.AllChaptersListModelItem
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters.MeaningModel
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.all_chapters.SummaryModel
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.chapter.ChapterModel
import com.ayushsinghal.bhagavadgita.features.slok.domain.model.slok.SlokModel
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response


class FakeRepository : BhagvadGitaRepository {
    override suspend fun getSlok(chapter: Int, verse: Int): Response<SlokModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChapterInformation(): Response<AllChaptersListModel> {
        val toSendError = false

        if (toSendError) {
            val errorBody = ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"message\": \"Unauthorized\"}"
            )
            return Response.error(403, errorBody)
        } else {
            val allChaptersListDTO = AllChaptersListModel()
            val allChaptersListDTOItem = AllChaptersListModelItem(
                chapter_number = 1,
                meaning = MeaningModel(en = "English Meaning", hi = "Hindi Meaning"),
                name = "Name",
                summary = SummaryModel(
                    en = "English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary English Summary ",
                    hi = "Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary Hindi Summary "
                ),
                transliteration = "Transliteration",
                translation = "Translation",
                verses_count = 987
            )
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            allChaptersListDTO.add(allChaptersListDTOItem)
            return Response.success(allChaptersListDTO)
        }
    }

    override suspend fun getChapterInformation(chapterNumber: Int): Response<ChapterModel> {
        TODO("Not yet implemented")
    }
}