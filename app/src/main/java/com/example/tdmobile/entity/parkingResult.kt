package com.example.tdmobile.entity

data class parkingResult(
    val command : String,
    val rowCount : Int,
    val rows : List<ParkingEntity>
)


