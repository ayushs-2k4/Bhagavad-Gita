package com.ayushsinghal.bhagavadgita.features.slok.data.remote

import com.ayushsinghal.bhagavadgita.features.slok.data.remote.dto.all_chapters_dto.AllChaptersListDTO
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.dto.chapter_dto.ChapterDTO
import com.ayushsinghal.bhagavadgita.features.slok.data.remote.dto.slok_dto.SlokDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BhagavadGitaApi {

    @GET("slok/{chapter}/{verse}")
    suspend fun getSlok(
        @Path("chapter") chapter: Int,
        @Path("verse") verse: Int
    ): Response<SlokDTO>


    @GET("chapters")
    suspend fun getAllChapterInformation(): Response<AllChaptersListDTO>

    @GET("chapter/{chapter_num}")
    suspend fun getChapterInformation(@Path("chapter_num") chapterNumber: Int): Response<ChapterDTO>
}