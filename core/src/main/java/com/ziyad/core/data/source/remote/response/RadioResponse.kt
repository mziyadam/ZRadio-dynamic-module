package com.ziyad.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RadioResponse(

    @field:SerializedName("stationuuid")
    val stationuuid: String? = "null",

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("url_resolved")
    val url_resolved: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("favicon")
    val favicon: String? = null,
) : Parcelable