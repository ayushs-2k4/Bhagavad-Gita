package com.ayushsinghal.bhagvadgita.features.slok.domain.repository

import com.ayushsinghal.bhagvadgita.features.slok.domain.model.all_chapters.AllChaptersListModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SlokModel
import retrofit2.Response

interface BhagvadGitaRepository {

    suspend fun getSlok(chapter: Int, verse: Int): Response<SlokModel>

    suspend fun getAllChapterInformation(): Response<AllChaptersListModel>


}