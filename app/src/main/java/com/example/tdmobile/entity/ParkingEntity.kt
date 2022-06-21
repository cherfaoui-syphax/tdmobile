package com.example.tdmobile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "parkings")
data class ParkingEntity(
    @PrimaryKey
    val parking_id: Int,
    @ColumnInfo(name = "nb_places")
    val nb_places: Int,
    @ColumnInfo(name = "nb_available_places")
    val nb_available_places: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "price")
    val price: Double,
    @Ignore
    val schedule: List<Horraire>
    )