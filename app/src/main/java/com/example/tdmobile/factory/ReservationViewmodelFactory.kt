package com.example.tdmobile.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tdmobile.ParkingViewModel
import com.example.tdmobile.ReservationViewModel
import com.example.tdmobile.repository.MainRepository
import com.example.tdmobile.repository.ReservationRepository

class ReservationViewmodelFactory constructor(private val repository: ReservationRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            ReservationViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}