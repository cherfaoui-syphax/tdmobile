package com.example.tdmobile.repository

import com.example.tdmobile.retrofit.RetrofitService

class ReservationRepository constructor(private val retrofitService: RetrofitService) {
    fun getMyReservations(uid : String) = RetrofitService.getInstance().getMyReservations(uid)
}