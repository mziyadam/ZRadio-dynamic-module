package com.ziyad.core.data.source.local.room

import androidx.room.*
import com.ziyad.core.data.source.local.entity.RadioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RadioDAO {
    @Query("SELECT * FROM radioEntity")
    fun getAllIndonesianRadio(): Flow<List<RadioEntity>>

    @Query("SELECT * FROM radioEntity where isFavorite = 1")
    fun getFavoriteRadio(): Flow<List<RadioEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRadio(radio: List<RadioEntity>)

    @Update
    fun updateFavoriteRadio(radio: RadioEntity)

    @Query("DELETE FROM radioEntity WHERE isFavorite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM radioEntity WHERE stationuuid = :stationuuid AND isFavorite = 1)")
    fun isFavorited(stationuuid: String): Boolean
}