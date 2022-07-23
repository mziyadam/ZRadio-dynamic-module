package com.ziyad.core.domain.usecase

import com.ziyad.core.domain.model.Radio
import com.ziyad.core.domain.repository.IRadioRepository
import kotlinx.coroutines.flow.Flow

class RadioInteractor(private val radioRepository: IRadioRepository) : RadioUseCase {
    override suspend fun getAll(): Flow<List<Radio>> {
        return radioRepository.getAll()
    }

    override fun getFavoriteRadio(): Flow<List<Radio>> {
        return radioRepository.getFavoriteRadio()
    }

    override fun setFavoriteRadio(radio: Radio, isFavorite: Boolean) {
        return radioRepository.setFavoriteRadio(radio, isFavorite)
    }

}