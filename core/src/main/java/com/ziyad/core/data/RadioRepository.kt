package com.ziyad.core.data

import com.ziyad.core.data.source.local.LocalDataSource
import com.ziyad.core.data.source.local.entity.RadioEntity
import com.ziyad.core.data.source.remote.RemoteDataSource
import com.ziyad.core.domain.model.Radio
import com.ziyad.core.domain.repository.IRadioRepository
import com.ziyad.core.utils.AppExecutors
import com.ziyad.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RadioRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IRadioRepository {

//    companion object {
//        @Volatile
//        private var instance: RadioRepository? = null
//
//        fun getInstance(
//            remoteData: RemoteDataSource,
//            localData: LocalDataSource,
//            appExecutors: AppExecutors
//        ): RadioRepository =
//            instance ?: synchronized(this) {
//                instance ?: RadioRepository(remoteData, localData, appExecutors)
//            }
//    }

    override fun getFavoriteRadio(): Flow<List<Radio>> {
        return localDataSource.getFavoriteRadio().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteRadio(radio: Radio, isFavorite: Boolean) {
        val radioEntity = DataMapper.mapDomainToEntity(radio)
        appExecutors.diskIO().execute { localDataSource.setFavoriteRadio(radioEntity, isFavorite) }
    }

    override suspend fun getAll(): Flow<List<Radio>> {
        var data = localDataSource.getAllIndonesianRadio().first()
        if (data.isEmpty()) {
            data = remoteDataSource.getAll().first()
        }
        val radios = data
        val radioList = ArrayList<RadioEntity>()
        radios.forEach { radioResponse ->
            val isFavorited = localDataSource.isFavorited(radioResponse.stationuuid)
            val radio = RadioEntity(
                radioResponse.stationuuid,
                radioResponse.name,
                radioResponse.state,
                radioResponse.url_resolved,
                radioResponse.favicon,
                isFavorited
            )
            radioList.add(radio)
        }
        localDataSource.deleteAll()
        localDataSource.insertRadio(radioList)
        return localDataSource.getAllIndonesianRadio().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
}
