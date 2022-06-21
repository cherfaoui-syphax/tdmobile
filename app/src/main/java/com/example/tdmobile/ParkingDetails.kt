package com.example.tdmobile
import android.location.Location
import java.time.LocalTime

data class ParkingDetails(val exactLocation: Pair<Double,Double>, val price: Double, val opens: List<Triple<String,String,String>>)