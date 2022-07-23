package com.ziyad.core.data.source.local

import com.ziyad.core.data.source.local.entity.RadioEntity
import com.ziyad.core.data.source.local.room.RadioDAO
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val radioDAO: RadioDAO) {

    fun getAllIndonesianRadio(): Flow<List<RadioEntity>> = radioDAO.getAllIndonesianRadio()

    fun getFavoriteRadio(): Flow<List<RadioEntity>> = radioDAO.getFavoriteRadio()

    suspend fun insertRadio(radioList: List<RadioEntity>) = radioDAO.insertRadio(radioList)

    fun isFavorited(stationuuid: String) = radioDAO.isFavorited(stationuuid)

    fun deleteAll() = radioDAO.deleteAll()

    fun setFavoriteRadio(tourism: RadioEntity, newState: Boolean) {
        tourism.isFavorite = newState
        radioDAO.updateFavoriteRadio(tourism)
    }
}