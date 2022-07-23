package com.ziyad.core.domain.usecase

import com.ziyad.core.domain.model.Radio
import kotlinx.coroutines.flow.Flow

interface RadioUseCase {
    suspend fun getAll(): Flow<List<Radio>>
    fun getFavoriteRadio(): Flow<List<Radio>>
    fun setFavoriteRadio(radio: Radio, isFavorite: Boolean)
}