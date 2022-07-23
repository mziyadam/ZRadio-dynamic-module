package com.ziyad.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Radio(
    val stationuuid: String,
    val name: String,
    val state: String,
    val url_resolved: String,
    val favicon: String,
    var isFavorite: Boolean = false
) : Parcelable