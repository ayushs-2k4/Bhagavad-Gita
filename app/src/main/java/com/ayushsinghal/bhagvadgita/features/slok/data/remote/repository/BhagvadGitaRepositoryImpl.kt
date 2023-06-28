package com.ayushsinghal.bhagvadgita.features.slok.data.remote.repository

import com.ayushsinghal.bhagvadgita.features.slok.data.remote.BhagvadGitaApi
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.all_chapters.AllChaptersListModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.all_chapters.AllChaptersListModelItem
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SlokModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.repository.BhagvadGitaRepository
import com.ayushsinghal.bhagvadgita.features.slok.mapper.AllChaptersInfoMappers
import com.ayushsinghal.bhagvadgita.features.slok.mapper.SlokMappers
import retrofit2.Response

class BhagvadGitaRepositoryImpl(
    private val bhagvadGitaApi: BhagvadGitaApi
) : BhagvadGitaRepository {
    override suspend fun getSlok(chapter: Int, verse: Int): Response<SlokModel> {
        val response = bhagvadGitaApi.getSlok(
            chapter = chapter,
            verse = verse
        )
        val mySlokDTO = response.body()
        if (response.isSuccessful && mySlokDTO != null) {
            val slok = SlokModel(
                _id = mySlokDTO._id,
                abhinav = SlokMappers.abhinavDTOToAbhinavModelMapper(mySlokDTO.abhinav),
                adi = SlokMappers.adiDTOToAdiModelMapper(mySlokDTO.adi),
                anand = SlokMappers.anandDTOToAnandModelMapper(mySlokDTO.anand),
                chapter = mySlokDTO.chapter,
                chinmay = SlokMappers.chinmayDTOToChinmayModelMapper(mySlokDTO.chinmay),
                dhan = SlokMappers.dhanDTOToDhanModelMapper(mySlokDTO.dhan),
                gambir = SlokMappers.gambirDTOToGambirModelMapper(mySlokDTO.gambir),
                jaya = SlokMappers.jayaDTOToJayaModelMapper(mySlokDTO.jaya),
                madhav = SlokMappers.madhavDTOToMadhavModelMapper(mySlokDTO.madhav),
                ms = SlokMappers.msDTOToMsModelMapper(mySlokDTO.ms),
                neel = SlokMappers.neelDTOToNeelModelMapper(mySlokDTO.neel),
                purohit = SlokMappers.purohitDTOToPurohitModelMapper(mySlokDTO.purohit),
                puru = SlokMappers.puruDTOToPuruModelMapper(mySlokDTO.puru),
                raman = SlokMappers.ramanDTOToRamanModelMapper(mySlokDTO.raman),
                rams = SlokMappers.ramsDTOToRamsModelMapper(mySlokDTO.rams),
                san = SlokMappers.sanDTOToSanModelMapper(mySlokDTO.san),
                sankar = SlokMappers.sankarDTOToSankarModelMapper(mySlokDTO.sankar),
                siva = SlokMappers.sivaDTOToSivaModelMapper(mySlokDTO.siva),
                slok = mySlokDTO.slok,
                srid = SlokMappers.sridDTOToSridModelMapper(mySlokDTO.srid),
                tej = SlokMappers.tejDTOToTejModelMapper(mySlokDTO.tej),
                transliteration = mySlokDTO.transliteration,
                vallabh = SlokMappers.vallabhDTOToVallabhModelMapper(mySlokDTO.vallabh),
                venkat = SlokMappers.venkatDTOToVenkatModelMapper(mySlokDTO.venkat),
                verse = mySlokDTO.verse
            )

            return Response.success(slok)
        } else {
            return Response.error(500, response.errorBody())
        }
    }

    override suspend fun getAllChapterInformation(): Response<AllChaptersListModel> {
        val response = bhagvadGitaApi.getAllChapterInformation()

        val allChaptersInfo = response.body()

        val allChaptersListModel = AllChaptersListModel()

        if (response.isSuccessful && allChaptersInfo != null) {
            val allChaptersModel = allChaptersInfo.map { allChaptersListDTOItem ->
                val allChaptersListModelItem = AllChaptersListModelItem(
                    chapter_number = allChaptersListDTOItem.chapter_number,
//                    meaning = allChaptersListDTOItem.meaning,
                    meaning = AllChaptersInfoMappers.meaningDTOToMeaningModelMapper(
                        allChaptersListDTOItem.meaning
                    ),
                    name = allChaptersListDTOItem.name,
                    summary = AllChaptersInfoMappers.summaryDTOToSummaryModelMapper(
                        allChaptersListDTOItem.summary
                    ),
                    translation = allChaptersListDTOItem.translation,
                    transliteration = allChaptersListDTOItem.transliteration,
                    verses_count = allChaptersListDTOItem.verses_count
                )

                allChaptersListModel.add(element = allChaptersListModelItem)
            }

            return Response.success(allChaptersListModel)
        } else {
            return Response.error(500, response.errorBody())
        }

    }
}
