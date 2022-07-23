package com.ziyad.core.domain.repository

import com.ziyad.core.domain.model.Radio
import kotlinx.coroutines.flow.Flow

interface IRadioRepository {
    suspend fun getAll(): Flow<List<Radio>>
    fun getFavoriteRadio(): Flow<List<Radio>>
    fun setFavoriteRadio(radio: Radio, isFavorite: Boolean)
}