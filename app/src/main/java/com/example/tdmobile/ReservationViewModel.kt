package com.example.tdmobile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieexample.isInternetAvailable
import com.example.tdmobile.database.AppDatabase
import com.example.tdmobile.entity.ReservationEntity
import com.example.tdmobile.entity.ReservationResponse
import com.example.tdmobile.entity.parkingsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.time.Duration.Companion.minutes
import com.example.tdmobile.repository.MainRepository
import com.example.tdmobile.repository.ReservationRepository
import com.mikepenz.iconics.Iconics.applicationContext


class ReservationViewModel constructor(private val repository: ReservationRepository , )  : ViewModel(){

    val reservationList = MutableLiveData<List<ReservationEntity>>();
    val errorMessage = MutableLiveData<String>();



    fun getReservationOffline(){
        try {
            val reservations  = AppDatabase.buildDatabase(applicationContext)?.getReservationDao()?.getAllReservations()
            reservationList.postValue(reservations!!);
        }catch (e: Exception){
            Log.d("Erreur get reservation sqlite",e.toString())
        }

    }

    fun saveReservationsLocally(reservation : MutableList<ReservationEntity>){
        Log.d("reservationList.value", reservation.toString());
        AppDatabase.buildDatabase(applicationContext)?.getReservationDao()?.truncateReservations()
        for (row in reservation){
            AppDatabase.buildDatabase(applicationContext)?.getReservationDao()?.insert(row)
        }

    }


    fun getReservationsOnline(uid:String){
        val response = repository.getMyReservations(uid)
        response.enqueue(object : Callback<ReservationResponse> {
            override fun onResponse(call: Call<ReservationResponse>, response: Response<ReservationResponse>) {
                Log.d("response" , response.toString());
                Log.d("response" , response.body()!!.toString())
                val data = response.body()!!
                val reservations = mutableListOf<ReservationEntity>()
                for (row in data.result.rows){
                    reservations.add(row)
                }
                reservationList.postValue(reservations);
                if(!reservations.isEmpty()!!){saveReservationsLocally(reservations);}
            }
            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }

    fun getMyReservations(uid:String){
        if(isInternetAvailable((applicationContext))){getReservationsOnline(uid)}else{getReservationOffline()}

    }




}