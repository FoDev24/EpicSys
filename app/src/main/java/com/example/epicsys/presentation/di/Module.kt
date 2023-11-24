package com.example.epicsys.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.epicsys.data.local.AirlineDao
import com.example.epicsys.data.local.AirlineDatabase
import com.example.epicsys.data.remote.AirlineApi
import com.example.epicsys.data.repository.AirlineRepositoryImp
import com.example.epicsys.domain.repository.AirlineRepository
import com.example.epicsys.util.Constants.Companion.BASE_URL
import com.example.epicsys.util.Constants.Companion.MEAL_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideAirlineDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AirlineDatabase::class.java,
        MEAL_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideAirlineDb(db: AirlineDatabase) = db.airlineDao()

    @Singleton
    @Provides
    fun provideAirlineRepository(
        dao: AirlineDao,
        api: AirlineApi
    ) = AirlineRepositoryImp(api, dao) as AirlineRepository


    @Provides
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideAirlineAPi(retrofit: Retrofit): AirlineApi =
        retrofit.create(AirlineApi::class.java)

}