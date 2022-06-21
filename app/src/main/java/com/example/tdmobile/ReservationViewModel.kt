package com.example.tdmobile

import androidx.lifecycle.ViewModel
import com.example.tdmobile.entity.ReservationEntity
import kotlin.time.Duration.Companion.minutes

class ReservationViewModel : ViewModel() {
    val reservationList = loadData()
    private fun loadData():List<ReservationEntity>{
        val data = mutableListOf<ReservationEntity>()
        data.add(ReservationEntity(1,1,1,"Hydra","12-05-1 11:00","12-17-2021  11:00" , 450,false))
        data.add(ReservationEntity(2,2,2,"Hydra","12-05-1 11:00","12-17-2021  11:00" , 450,false))
        data.add(ReservationEntity(3,3,3,"Hydra","12-05-1 11:00","12-17-2021  11:00" , 450,false))

        return data
    }



}