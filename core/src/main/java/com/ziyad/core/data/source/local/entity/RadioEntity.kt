package com.ziyad.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class RadioEntity(
    @PrimaryKey
    @ColumnInfo(name = "stationuuid")
    @field:SerializedName("stationuuid")
    val stationuuid: String,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String,

    @ColumnInfo(name = "state")
    @field:SerializedName("state")
    val state: String,

    @ColumnInfo(name = "url")
    @field:SerializedName("url_resolved")
    val url_resolved: String,

    @ColumnInfo(name = "favicon")
    @field:SerializedName("favicon")
    val favicon: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable