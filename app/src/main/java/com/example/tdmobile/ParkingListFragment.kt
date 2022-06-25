package com.example.tdmobile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ParkinglistFragmentBinding
import com.example.tdmobile.factory.MyViewModelFactory
import com.example.tdmobile.repository.MainRepository
import com.example.tdmobile.retrofit.RetrofitService.Companion.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.minutes
import com.example.tdmobile.RecyclerAdapter
import com.example.tdmobile.retrofit.RetrofitService

class ParkingListFragment: Fragment() {
    private var _binding: ParkinglistFragmentBinding? = null

    private val retrofitService = RetrofitService.getInstance()
    private lateinit var adapter : RecyclerAdapter ;
    private lateinit var layoutManager : LinearLayoutManager;
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


        val viewModel = ViewModelProvider((activity as MainActivity), MyViewModelFactory(MainRepository(retrofitService!!))).get(ParkingViewModel::class.java)

        viewModel.parkingList.observe(viewLifecycleOwner, Observer {

            Log.d(TAG, "onCreate: $it")
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            binding.recyclerView.layoutManager = layoutManager
            adapter = RecyclerAdapter(requireContext());
            adapter.setParkingList(it)
            binding.recyclerView.adapter = adapter


        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {



        })
        viewModel.getAllParkings()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}