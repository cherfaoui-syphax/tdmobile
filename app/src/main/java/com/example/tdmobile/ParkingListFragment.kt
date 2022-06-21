package com.example.tdmobile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ParkinglistFragmentBinding
import com.example.tdmobile.retrofit.Endpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.minutes

class ParkingListFragment: Fragment() {
    private var _binding: ParkinglistFragmentBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ParkingListFragment()
    }

    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.clearDisappearingChildren()
        _binding = ParkinglistFragmentBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm = ViewModelProvider(this).get(ParkingViewModel::class.java)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.recyclerView.layoutManager = layoutManager

        CoroutineScope(Dispatchers.IO).launch {
            val response = Endpoint.createInstance().getAllParkings()
            Log.d("response", response.toString());
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val rows = data.result.rows
                    Log.d("logged",rows.toString())
                    val parkingList = mutableListOf<Parking>()
                    val opens = mutableListOf<Triple<String,String,String>>()
                    for(row in rows){
                        opens.add(Triple("Dimanche",  row.schedule[0].ouverture,row.schedule[0].frmeture))
                        opens.add(Triple("Dimanche",  row.schedule[1].ouverture,row.schedule[1].frmeture))
                        parkingList.add(Parking(row.name,"","Fermé",8.86, row.nb_available_places,14.minutes, ParkingDetails(Pair(row.latitude,row.longitude),row.price, opens)))
                        opens.clear()

                    }
                    val adapter = RecyclerAdapter(requireContext(),parkingList)
                    binding.recyclerView.adapter = adapter
                }
            }
        }





    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}