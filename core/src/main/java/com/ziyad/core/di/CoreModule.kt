@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.ziyad.core.di

import androidx.room.Room
import com.ziyad.core.data.RadioRepository
import com.ziyad.core.data.source.local.LocalDataSource
import com.ziyad.core.data.source.local.room.RadioDatabase
import com.ziyad.core.data.source.remote.RemoteDataSource
import com.ziyad.core.data.source.remote.network.ApiService
import com.ziyad.core.domain.repository.IRadioRepository
import com.ziyad.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("ziyad".toCharArray())
        val factory = SupportFactory(passphrase)
        //build
        Room.databaseBuilder(
                androidContext(),
                RadioDatabase::class.java, "radio.db"
            )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "at1.api.radio-browser.info"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/g2XfkizzzilfPAYwv7WOs0zFmWEZPMwHzFQIPdxiTQ4=")
            .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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