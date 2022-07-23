package com.ziyad.zradio.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ziyad.core.domain.model.Radio

class PlayViewModel : ViewModel() {
    private val _radio = MutableLiveData<Radio>()
    val radio: LiveData<Radio>
        get() = _radio

    fun setRadio(radio: Radio) {
        _radio.value = radio
    }
}