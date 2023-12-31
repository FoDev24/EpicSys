package com.example.epicsys.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "airlinesInformation")
data class AirlineItem(
    val __clazz: String,
    val alliance: String,
    @PrimaryKey
    val code: String,
    val defaultName: String,
    val logoURL: String,
    val name: String,
    val phone: String,
    val site: String,
    val usName: String
) : Parcelable