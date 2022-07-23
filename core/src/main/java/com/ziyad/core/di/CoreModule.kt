package com.ziyad.core.di

import androidx.room.Room
import com.ziyad.core.data.source.remote.network.ApiService
import com.ziyad.core.data.RadioRepository
import com.ziyad.core.data.source.local.LocalDataSource
import com.ziyad.core.data.source.local.room.RadioDatabase
import com.ziyad.core.data.source.remote.RemoteDataSource
import com.ziyad.core.domain.repository.IRadioRepository
import com.ziyad.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<RadioDatabase>().radioDAO() }
    single {
        Room.databaseBuilder(
            androidContext(),
            RadioDatabase::class.java, "radio_database"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://at1.api.radio-browser.info/json/stations/bycountry/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IRadioRepository> { RadioRepository(get(), get(), get()) }
}