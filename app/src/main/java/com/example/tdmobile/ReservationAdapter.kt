package com.example.tdmobile

import com.example.tdmobile.entity.ReservationEntity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ReservationLayoutBinding
import com.example.tdmobile.databinding.ParkingLayoutBinding
import com.example.tdmobile.entity.commentEntity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class ReservationAdapter(
    val context: Context,
): RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    var data = mutableListOf<ReservationEntity>()
    fun setReservayionList(reservations: List<ReservationEntity>) {
        this.data = reservations.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ReservationLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }



    override fun getItemCount() = data.size
    class ViewHolder(val binding: ReservationLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var navController: NavController? = null
        holder.binding.apply {
            startTime.text = data[position].start_time
            endTime.text = data[position].end_time
            adresse.text = data[position].adresse
            price.text = data[position].prix.toString()
        }
        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(ReservationListDirections.actionReservationListFragmentToParkingDetailsFragment(position))
        }
    }
}
