package com.example.epicsys.domain.repository

import com.example.epicsys.domain.model.AirlineItem
import com.example.epicsys.util.Resource

interface AirlineRepository {
    suspend fun insertDbAirline(airline : AirlineItem)
    suspend fun deleteDbAirline(airline : AirlineItem)
    suspend fun showAllDbAirlines():List<AirlineItem>
    suspend fun getAllAirlines():Resource<List<AirlineItem>>




}