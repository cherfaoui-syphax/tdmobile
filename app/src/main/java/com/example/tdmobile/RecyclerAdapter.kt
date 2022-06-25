package com.example.tdmobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ParkingLayoutBinding
import com.example.tdmobile.ParkingListFragmentDirections

class RecyclerAdapter(val context: Context):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var data = mutableListOf<Parking>()
    fun setParkingList(parkings: List<Parking>) {
        this.data = parkings.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ParkingLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var navController: NavController? = null
        holder.binding.apply {
            nameTextView.text = data[position].name
            stateTextView.text = data[position].state
            locationTextView.text = data[position].location
            distanceTextView.text = String.format("%.1f km", data[position].distance)
            timeTextView.text = data[position].time.toString()
            occupationTextView.text = String.format("%d%%",data[position].occupation,"%")
        }
        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(ParkingListFragmentDirections.actionParkingListFragmentToParkingDetailsFragment("",position))

        }
    }

    override fun getItemCount() = data.size


    class ViewHolder(val binding: ParkingLayoutBinding): RecyclerView.ViewHolder(binding.root)
}