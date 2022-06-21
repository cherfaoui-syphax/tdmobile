package com.example.tdmobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ReservationListLayoutBinding

class ReservationList : Fragment(){
    private var _binding: ReservationListLayoutBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ReservationList()
    }

    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.clearDisappearingChildren()
        _binding = ReservationListLayoutBinding.inflate(layoutInflater)

        var preferences: SharedPreferences? =
            this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)



        val con = preferences!!.getBoolean("connected", false)
        Log.d("connected", con.toString());

        if(!con)   findNavController().navigate(ReservationListDirections.actionReservationListFragmentToSignInFragment())





        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)




        binding.recyclerView.layoutManager = layoutManager
        val vm = ViewModelProvider(this).get(ReservationViewModel::class.java)
        val adapter = ReservationAdapter(requireContext(),vm.reservationList)
        binding.recyclerView.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}