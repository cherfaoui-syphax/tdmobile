package com.example.tdmobile

import kotlin.time.ExperimentalTime

data class Parking @OptIn(ExperimentalTime::class) constructor(
    val name: String, val location: String,
    val state: String, val distance: Double,
    val occupation: Int, val time: kotlin.time.Duration,
    val details: ParkingDetails?
)