package com.example.tdmobile

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.ParkingDetailsLayoutBinding
import com.example.tdmobile.factory.MyViewModelFactory
import com.example.tdmobile.repository.MainRepository
import com.example.tdmobile.retrofit.RetrofitService.Companion.retrofitService
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.time.ExperimentalTime

class ParkingDetailsFragment: Fragment() {
    private val args: ParkingDetailsFragmentArgs by navArgs()
    private var _binding: ParkingDetailsLayoutBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    @OptIn(ExperimentalTime::class)
    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= ParkingDetailsLayoutBinding.inflate(layoutInflater)

        val viewModel = ViewModelProvider((activity as MainActivity), MyViewModelFactory(MainRepository(retrofitService!!))).get(ParkingViewModel::class.java)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)




        val parking = viewModel.parkingList.value!!.get(args.parking)
        println(parking.toString());




        binding.locationDetailsTextView.text= parking.location
        binding.distanceDetailsTextView.text= String.format("%.1f km", parking.distance)
        binding.timeDetailsTextView.text=parking.time.toString()
        binding.nameDetailsTextView.text=parking.name
        binding.nameDetailsTextView2.text=parking.name
        binding.occupationDetailsTextView.text= String.format("%d %%",parking.occupation)
        binding.stateDetailsTextView.text=parking.state
        binding.opensRecyclerView.layoutManager = layoutManager

        var ratingBar = binding.ratingBar2;


        val view = layoutInflater.inflate(R.layout.fragment_reserver_fragment, null)

        // on below line we are creating a variable for our button
        // which we are using to dismiss our dialog.
        val dialog = BottomSheetDialog(requireContext())
        val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)
        val timePicker1 = view.findViewById<TimePicker>(R.id.timePicker1)
        val timePicker2 = view.findViewById<TimePicker>(R.id.timePicker2)
        timePicker1.setIs24HourView(true)
        timePicker2.setIs24HourView(true)
        // on below line we are adding on click listener
        // for our dismissing the dialog button.
        btnClose.setOnClickListener {
            // on below line we are calling a dismiss
            // method to close our dialog.
            dialog.dismiss()
        }
        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)


        binding.openBottomSheet.setOnClickListener {
            // on below line we are creating a new bottom sheet dialog.

            // on below line we are inflating a layout file which we have created.


            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()
        }
        binding.button.setOnClickListener {
            val uri =
                "http://maps.google.com/maps?daddr=" + parking.details?.exactLocation?.first.toString() + "," + parking.details?.exactLocation?.second.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
        val adapter = context?.let { parking.details?.let { it1 ->
            OpensRecyclerAdapter(it,
                it1.opens)
        } }
        binding.opensRecyclerView.adapter = adapter
        NavHostFragment
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}