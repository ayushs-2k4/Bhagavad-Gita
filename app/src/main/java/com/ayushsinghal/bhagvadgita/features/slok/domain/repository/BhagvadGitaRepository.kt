package com.ayushsinghal.bhagvadgita.features.slok.domain.repository

import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.Slok
import retrofit2.Response

interface BhagvadGitaRepository {

    suspend fun getSlok(chapter: Int, verse: Int): Response<Slok>

}