package com.example.epicsys.data.repository

import android.util.Log
import com.example.epicsys.data.local.AirlineDao
import com.example.epicsys.data.remote.AirlineApi
import com.example.epicsys.domain.model.AirlineItem
import com.example.epicsys.domain.repository.AirlineRepository
import com.example.epicsys.util.Resource
import javax.inject.Inject

class AirlineRepositoryImp @Inject constructor(
    private val airlineApi : AirlineApi,
    private val airlineDao : AirlineDao
):AirlineRepository {



    override suspend fun insertDbAirline(airline: AirlineItem) {
        airlineDao.upsert(airline)
    }

    override suspend fun deleteDbAirline(airline: AirlineItem) {
        airlineDao.delete(airline)
    }

    override suspend fun showAllDbAirlines(): List<AirlineItem> {
        return airlineDao.getAllAirlines()
    }

    override suspend fun showAllApiAirlines(): Resource<List<AirlineItem>> {
        return try {
            val response = airlineApi.getAllAirlines()
            Log.d("repo",response.body().toString())

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("An unknown error.", null)
            } else {
                Resource.Error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.Error("Couldn't reach the server.Check internet connection", null)
        }
    }
}