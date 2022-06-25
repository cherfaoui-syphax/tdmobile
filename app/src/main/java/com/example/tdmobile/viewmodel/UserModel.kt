package com.example.tdmobile.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.menuapplication.User
import com.example.menuapplication.clearUserId
import com.example.menuapplication.saveUserID
import com.example.tdmobile.Parking
import com.example.tdmobile.ParkingDetails
import com.example.tdmobile.entity.loginResponse
import com.example.tdmobile.entity.parkingsResponse
import com.example.tdmobile.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.time.Duration.Companion.minutes


class UserModel constructor(private val repository: MainRepository , )  : ViewModel() {

    var userId = MutableLiveData<String?>()
    var message = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String>();




    fun logout(activity: FragmentActivity) {
        var preferences: SharedPreferences? =
            activity.getSharedPreferences("pref", Context.MODE_PRIVATE)
        preferences!!.edit {
            putBoolean("connected",false)
            putString("email","")
            putString("usersId" ,"")
        }


    }
}