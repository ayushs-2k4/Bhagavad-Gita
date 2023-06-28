package com.ayushsinghal.bhagvadgita.features.slok.data.remote

import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.SlokDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BhagvadGitaApi {

    @GET("slok/{chapter}/{verse}")
    suspend fun getSlok(
        @Path("chapter") chapter: Int,
        @Path("verse") verse: Int
    ): Response<SlokDTO>


}