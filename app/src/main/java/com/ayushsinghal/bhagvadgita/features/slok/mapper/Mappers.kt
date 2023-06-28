package com.ayushsinghal.bhagvadgita.features.slok.mapper

import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Abhinav
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Adi
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Anand
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Chinmay
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Dhan
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Gambir
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Jaya
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Madhav
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Ms
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Neel
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Purohit
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Puru
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Raman
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Rams
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.San
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Sankar
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Siva
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Srid
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Tej
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Vallabh
import com.ayushsinghal.bhagvadgita.features.slok.data.remote.dto.slok_dto.Venkat
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.AbhinavModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.AdiModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.AnandModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.ChinmayModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.DhanModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.GambirModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.JayaModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.MadhavModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.MsModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.NeelModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.PurohitModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.PuruModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.RamanModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.RamsModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SanModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SankarModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SivaModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.SridModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.TejModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.VallabhModel
import com.ayushsinghal.bhagvadgita.features.slok.domain.model.slok.VenkatModel

object Mappers {

    fun abhinavDTOToAbhinavModelMapper(abhinavDTO: Abhinav): AbhinavModel {
        return AbhinavModel(
            author = abhinavDTO.author,
            et = abhinavDTO.et,
            sc = abhinavDTO.sc
        )
    }

    fun adiDTOToAdiModelMapper(adiDTO: Adi): AdiModel {
        return AdiModel(
            author = adiDTO.author,
            et = adiDTO.et
        )
    }

    fun anandDTOToAnandModelMapper(anandDTO: Anand): AnandModel {
        return AnandModel(
            author = anandDTO.author,
            sc = anandDTO.sc
        )
    }

    fun chinmayDTOToChinmayModelMapper(chinmayDTO: Chinmay): ChinmayModel {
        return ChinmayModel(
            author = chinmayDTO.author,
            hc = chinmayDTO.hc
        )
    }

    fun dhanDTOToDhanModelMapper(dhanDTO: Dhan): DhanModel {
        return DhanModel(
            author = dhanDTO.author,
            sc = dhanDTO.sc
        )
    }

    fun gambirDTOToGambirModelMapper(gambirDTO: Gambir): GambirModel {
        return GambirModel(
            author = gambirDTO.author,
            et = gambirDTO.et
        )
    }

    fun jayaDTOToJayaModelMapper(jayaDTO: Jaya): JayaModel {
        return JayaModel(
            author = jayaDTO.author,
            sc = jayaDTO.sc
        )
    }

    fun madhavDTOToMadhavModelMapper(madhavDTO: Madhav): MadhavModel {
        return MadhavModel(
            author = madhavDTO.author,
            sc = madhavDTO.sc
        )
    }

    fun msDTOToMsModelMapper(msDTO: Ms): MsModel {
        return MsModel(
            author = msDTO.author,
            sc = msDTO.sc
        )
    }

    fun neelDTOToNeelModelMapper(neelDTO: Neel): NeelModel {
        return NeelModel(
            author = neelDTO.author,
            sc = neelDTO.sc
        )
    }

    fun purohitDTOToPurohitModelMapper(purohitDTO: Purohit): PurohitModel {
        return PurohitModel(
            author = purohitDTO.author,
            et = purohitDTO.et
        )
    }

    fun puruDTOToPuruModelMapper(puruDTO: Puru): PuruModel {
        return PuruModel(
            author = puruDTO.author,
            sc = puruDTO.sc
        )
    }

    fun ramanDTOToRamanModelMapper(ramanDTO: Raman): RamanModel {
        return RamanModel(
            author = ramanDTO.author,
            et = ramanDTO.et,
            sc = ramanDTO.sc
        )
    }

    fun ramsDTOToRamsModelMapper(ramsDTO: Rams): RamsModel {
        return RamsModel(
            author = ramsDTO.author,
            hc = ramsDTO.hc,
            ht = ramsDTO.ht
        )
    }

    fun sanDTOToSanModelMapper(sanDTO: San): SanModel {
        return SanModel(
            author = sanDTO.author,
            et = sanDTO.et
        )
    }

    fun sankarDTOToSankarModelMapper(sankarDTO: Sankar): SankarModel {
        return SankarModel(
            author = sankarDTO.author,
            et = sankarDTO.et,
            ht = sankarDTO.ht,
            sc = sankarDTO.sc
        )
    }

    fun sivaDTOToSivaModelMapper(sivaDTO: Siva): SivaModel {
        return SivaModel(
            author = sivaDTO.author,
            ec = sivaDTO.ec,
            et = sivaDTO.et
        )
    }

    fun sridDTOToSridModelMapper(sridDTO: Srid): SridModel {
        return SridModel(
            author = sridDTO.author,
            sc = sridDTO.sc
        )
    }

    fun tejDTOToTejModelMapper(tejDTO: Tej): TejModel {
        return TejModel(
            author = tejDTO.author,
            ht = tejDTO.ht
        )
    }

    fun vallabhDTOToVallabhModelMapper(vallabhDTO: Vallabh): VallabhModel {
        return VallabhModel(
            author = vallabhDTO.author,
            sc = vallabhDTO.sc
        )
    }

    fun venkatDTOToVenkatModelMapper(venkatDTO: Venkat): VenkatModel {
        return VenkatModel(
            author = venkatDTO.author,
            sc = venkatDTO.sc
        )
    }
}