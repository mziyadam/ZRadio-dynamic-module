package com.ziyad.core.utils

import com.ziyad.core.domain.model.Radio
import com.ziyad.core.data.source.remote.response.RadioResponse
import com.ziyad.core.data.source.local.entity.RadioEntity

object DataMapper {
    fun mapResponsesToEntities(input: List<RadioResponse>): List<RadioEntity> {
        val tourismList = ArrayList<RadioEntity>()
        input.map {
            val tourism = RadioEntity(
                it.stationuuid!!,
                it.name!!,
                it.state!!,
                it.url_resolved!!,
                it.favicon!!,
                true
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<RadioEntity>): List<Radio> =
        input.map {
            Radio(
                it.stationuuid,
                it.name,
                it.state,
                it.url_resolved,
                it.favicon,
                it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Radio) = RadioEntity(
        input.stationuuid,
        input.name,
        input.state,
        input.url_resolved,
        input.favicon,
        input.isFavorite
    )
}