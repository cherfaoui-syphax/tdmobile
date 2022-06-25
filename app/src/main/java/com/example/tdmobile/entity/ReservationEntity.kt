package com.example.tdmobile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "reservations"
)
data class ReservationEntity (
    @PrimaryKey
    val reservation_id : Int,
    val user_reservation : Int? ,
    @ColumnInfo(name = "parking_reservation")
    val parking_reservation : Int? ,
    @ColumnInfo(name = "adresse")
    val adresse : String? ,
    @ColumnInfo(name = "start_time")
    val start_time : String? ,
    @ColumnInfo(name = "end_time")
    val end_time : String?,
    @ColumnInfo(name = "prix")
    val prix : Int?,
    @ColumnInfo(name = "is_over")
    val is_over: Boolean?

)