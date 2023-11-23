package com.example.epicsys.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.epicsys.domain.model.AirlineItem

@Database(entities = [AirlineItem :: class], version = 1)
abstract class AirlineDatabase: RoomDatabase() {

    abstract fun airlineDao():AirlineDao
}