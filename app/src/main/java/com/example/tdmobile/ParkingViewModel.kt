package com.example.tdmobile
import androidx.lifecycle.ViewModel
import kotlin.time.Duration.Companion.minutes

class ParkingViewModel:ViewModel() {
    val parkingList = loadData();

    private fun loadData():List<Parking>{
        val data = mutableListOf<Parking>()
        val opens = mutableListOf<Triple<String,String,String>>()
        opens.add(Triple("Dimanche",  "8:00","19:00"))
        opens.add(Triple("Lundi",  "8:00","19:00"))
        data.add(Parking("Parking Jardin d'essai","Alger","Fermé",8.86, 65,14.minutes,
            ParkingDetails(Pair(36.7503406,3.0757269),200.0, opens)))
        opens.clear()
        opens.add(Triple("Dimanche", "8:00","19:00"))
        opens.add(Triple("Mardi",  "8:00","19:00"))
        data.add(Parking("Parking Said Hamdine","Alger","Fermé",12.95, 30,17.minutes,
            ParkingDetails(Pair(36.7347299,3.0233267),300.0, opens)))
        opens.clear()
        opens.add(Triple("Dimanche",  "8:00","19:00"))
        opens.add(Triple("Mercredi",  "8:00","19:00"))
        data.add(Parking("Parking Val d'Hydra","Alger","Fermé",12.95, 95,17.minutes,
            ParkingDetails(Pair(36.7565952,3.0266129),400.0, opens)))
        opens.clear()
        opens.add(Triple("Dimanche",  "8:00","19:00"))
        opens.add(Triple("Mercredi",  "8:00","19:00"))
        data.add(Parking("Parking Val d'Hydra","Alger","Fermé",12.95, 95,17.minutes,
            ParkingDetails(Pair(36.7565952,3.0266129),400.0, opens)))
        return data
    }
}