package com.ayushsinghal.bhagvadgita.features.slok.data.remote.repository

import com.ayushsinghal.bhagvadgita.features.slok.data.remote.BhagvadGitaApi
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.Slok
import com.ayushsinghal.bhagvadgita.features.slok.domain.repository.BhagvadGitaRepository
import com.ayushsinghal.bhagvadgita.features.slok.mapper.Mappers
import retrofit2.Response

class BhagvadGitaRepositoryImpl(
    private val bhagvadGitaApi: BhagvadGitaApi
) : BhagvadGitaRepository {
    override suspend fun getSlok(chapter: Int, verse: Int): Response<Slok> {
        val response = bhagvadGitaApi.getSlok(
            chapter = chapter,
            verse = verse
        )
        val mySlokDTO = response.body()
        if (response.isSuccessful && mySlokDTO != null) {
            val slok = Slok(
                _id = mySlokDTO._id,
                abhinav = Mappers.abhinavDTOToAbhinavModelMapper(mySlokDTO.abhinav),
                adi = Mappers.adiDTOToAdiModelMapper(mySlokDTO.adi),
                anand = Mappers.anandDTOToAnandModelMapper(mySlokDTO.anand),
                chapter = mySlokDTO.chapter,
                chinmay = Mappers.chinmayDTOToChinmayModelMapper(mySlokDTO.chinmay),
                dhan = Mappers.dhanDTOToDhanModelMapper(mySlokDTO.dhan),
                gambir = Mappers.gambirDTOToGambirModelMapper(mySlokDTO.gambir),
                jaya = Mappers.jayaDTOToJayaModelMapper(mySlokDTO.jaya),
                madhav = Mappers.madhavDTOToMadhavModelMapper(mySlokDTO.madhav),
                ms = Mappers.msDTOToMsModelMapper(mySlokDTO.ms),
                neel = Mappers.neelDTOToNeelModelMapper(mySlokDTO.neel),
                purohit = Mappers.purohitDTOToPurohitModelMapper(mySlokDTO.purohit),
                puru = Mappers.puruDTOToPuruModelMapper(mySlokDTO.puru),
                raman = Mappers.ramanDTOToRamanModelMapper(mySlokDTO.raman),
                rams = Mappers.ramsDTOToRamsModelMapper(mySlokDTO.rams),
                san = Mappers.sanDTOToSanModelMapper(mySlokDTO.san),
                sankar = Mappers.sankarDTOToSankarModelMapper(mySlokDTO.sankar),
                siva = Mappers.sivaDTOToSivaModelMapper(mySlokDTO.siva),
                slok = mySlokDTO.slok,
                srid = Mappers.sridDTOToSridModelMapper(mySlokDTO.srid),
                tej = Mappers.tejDTOToTejModelMapper(mySlokDTO.tej),
                transliteration = mySlokDTO.transliteration,
                vallabh = Mappers.vallabhDTOToVallabhModelMapper(mySlokDTO.vallabh),
                venkat = Mappers.venkatDTOToVenkatModelMapper(mySlokDTO.venkat),
                verse = mySlokDTO.verse
            )

            return Response.success(slok)
        } else {
            val errorResponse = ErrorResponse("An error occurred")
            return Response.error(500, response.errorBody())
        }
    }
}

data class ErrorResponse(val message: String)