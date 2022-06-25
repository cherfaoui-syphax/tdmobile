package com.example.tdmobile

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ReservationListLayoutBinding
import com.example.tdmobile.entity.ReservationEntity
import com.example.tdmobile.factory.MyViewModelFactory
import com.example.tdmobile.factory.ReservationViewmodelFactory
import com.example.tdmobile.repository.MainRepository
import com.example.tdmobile.repository.ReservationRepository
import com.example.tdmobile.retrofit.RetrofitService

class ReservationList : Fragment(){
    private var _binding: ReservationListLayoutBinding? = null
    // This property is only valid between onCreateView and

    private val retrofitService = RetrofitService.getInstance()
    private lateinit var adapter : ReservationAdapter ;
    private lateinit var layoutManager : LinearLayoutManager;
    private  var userId : String? = "";


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

        if(!con){   findNavController().navigate(ReservationListDirections.actionReservationListFragmentToSignInFragment())}
        else{userId = preferences!!.getString("usersId", "") }


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider((activity as MainActivity), ReservationViewmodelFactory(
            ReservationRepository(retrofitService!!)
        )
        ).get(ReservationViewModel::class.java)

        viewModel.reservationList.observe(viewLifecycleOwner, Observer { it : List<ReservationEntity>->

            Log.d(ContentValues.TAG, "onCreate: $it")
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            binding.recyclerView.layoutManager = layoutManager
            adapter = ReservationAdapter(requireContext());
            adapter.setReservayionList(it)
            binding.recyclerView.adapter = adapter


        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {



        })
        if(userId != "" && userId != null )viewModel.getMyReservations(userId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}