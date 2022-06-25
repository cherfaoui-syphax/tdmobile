package com.example.tdmobile
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tdmobile.entity.parkingsResponse
import com.example.tdmobile.repository.MainRepository
import com.example.tdmobile.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.DayOfWeek
import kotlin.time.Duration.Companion.minutes





class ParkingViewModel constructor(private val repository: MainRepository , )  : ViewModel() {


    val parkingList = MutableLiveData<List<Parking>>();
    val errorMessage = MutableLiveData<String>();




    fun putParkings(activity: FragmentActivity){
        var preferences: SharedPreferences? =
            activity.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonSring: String = gson.toJson(parkingList)
        preferences!!.edit {
            putString("parkingJson",jsonSring)
        }

        Log.d("TAG",jsonSring);

    }

    fun getSavedParkings(activity: FragmentActivity) : MutableList<Parking>{


        var preferences: SharedPreferences? =
            activity.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val parkingJson = preferences!!.getString("parkingJson" , "" )

        Log.d("parking json",parkingJson!!);

        var parkingList  = mutableListOf<Parking>();

        val gson = Gson()

        val mutableListParkingType = object : TypeToken<MutableList<Parking>>() {}.type

        parkingList= gson.fromJson(parkingJson, mutableListParkingType)

        Log.d("Json object" , parkingList.toString())

        return parkingList;
    }




    fun getAllParkings() {
        val response = repository.getAllParkings()
        response.enqueue(object : Callback<parkingsResponse> {
            override fun onResponse(call: Call<parkingsResponse>, response: Response<parkingsResponse>) {
                Log.d("response" , response.toString());
                val data = response.body()!!
                val rows = data.result.rows
                Log.d("logged",rows.toString())
                val parkings = mutableListOf<Parking>()
                for(row in rows){
                    val opens = mutableListOf<Triple<String,String,String>>()
                    for(sched in row.schedule) {
                        opens.add(Triple(sched.jour.toString(), sched.ouverture, sched.frmeture))
                    }
                    Log.d("opens",opens.toString());
                    parkings.add(Parking(row.name,"","Fermé",8.86, row.nb_available_places,14.minutes, ParkingDetails(Pair(row.latitude,row.longitude),row.price, opens)))
                }




                Log.d("tag parking list" , parkings.toString());




                parkingList.postValue(parkings);
            }
            override fun onFailure(call: Call<parkingsResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }




}
