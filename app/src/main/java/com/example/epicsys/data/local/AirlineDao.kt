package com.example.epicsys.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.epicsys.domain.model.AirlineItem

@Dao
interface AirlineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(airline : AirlineItem)

    @Delete
    suspend fun delete(airline: AirlineItem)

    @Query("SELECT * FROM airlinesInformation")
    suspend fun getAllAirlines():List<AirlineItem>



}