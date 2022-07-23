package com.ziyad.core.data.source.remote.network

import com.ziyad.core.data.source.remote.response.RadioResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("Indonesia")
    suspend fun getAllIndonesianRadio(): Response<List<RadioResponse>>
}