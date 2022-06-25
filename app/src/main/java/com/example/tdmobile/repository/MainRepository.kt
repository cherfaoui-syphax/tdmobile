package com.example.tdmobile.repository

import com.example.tdmobile.retrofit.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllParkings() = RetrofitService.getInstance().getAllParkings()
}