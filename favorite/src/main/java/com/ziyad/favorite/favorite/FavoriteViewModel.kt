package com.ziyad.favorite.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.domain.usecase.RadioUseCase
import com.ziyad.core.utils.player.RadioPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteViewModel(private val radioUseCase: RadioUseCase) : ViewModel() {
    val currentRadio = MutableLiveData<RadioPlayer?>()

    suspend fun getFavoriteRadio(): LiveData<List<Radio>> = withContext(
        Dispatchers.IO
    )
    { radioUseCase.getFavoriteRadio().asLiveData() }

    fun setFavoriteRadio(radio: Radio, isFavorite: Boolean) {
        radioUseCase.setFavoriteRadio(radio, isFavorite)
    }

    fun getCurrentRadio() {
        currentRadio.value = RadioPlayer.getPlayer()
    }
}