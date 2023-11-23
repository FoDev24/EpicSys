package com.example.epicsys.data.remote

import com.example.epicsys.domain.model.AirlineItem
import retrofit2.Response
import retrofit2.http.GET

interface AirlineApi {
    @GET("h/mobileapis/directory/airlines")
    suspend fun getAllAirlines () : Response<List<AirlineItem>>
}