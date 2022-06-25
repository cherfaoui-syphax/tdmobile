package com.example.tdmobile.entity

data class ReservationResult(
    val command : String,
    val rowCount : Int,
    val rows : List<ReservationEntity>
)