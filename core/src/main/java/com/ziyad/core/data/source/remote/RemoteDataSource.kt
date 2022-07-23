package com.ziyad.core.data.source.remote

import android.util.Log
import com.ziyad.core.data.source.remote.network.ApiService
import com.ziyad.core.data.source.local.entity.RadioEntity
import com.ziyad.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAll(): Flow<List<RadioEntity>> {
        return flow {
            try {
                val response = apiService.getAllIndonesianRadio().body()
                emit(DataMapper.mapResponsesToEntities(response!!))
                Log.e("ERROR", response.toString())
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }
}