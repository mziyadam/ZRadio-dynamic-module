package com.ziyad.zradio.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.domain.usecase.RadioUseCase
import com.ziyad.core.utils.player.RadioPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(private val radioUseCase: RadioUseCase) : ViewModel() {
    suspend fun getAllIndonesianRadio(): LiveData<List<Radio>> = withContext(
        Dispatchers.IO
    )
    { radioUseCase.getAll().asLiveData() }

    fun setFavoriteRadio(radio: Radio, isFavorite: Boolean) {
        radioUseCase.setFavoriteRadio(radio, isFavorite)
    }

    val currentRadio = MutableLiveData<RadioPlayer?>()
    fun getCurrentRadio() {
        currentRadio.value = RadioPlayer.getPlayer()
    }
}