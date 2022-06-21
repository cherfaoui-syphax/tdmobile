package com.example.tdmobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tdmobile.databinding.AccountDetailLayoutBinding
import com.example.tdmobile.databinding.ActivitySignInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.time.ExperimentalTime

class AccountDetailFragment : Fragment() {
    private var _binding: AccountDetailLayoutBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    @OptIn(ExperimentalTime::class)
    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AccountDetailLayoutBinding.inflate(layoutInflater)
        var preferences: SharedPreferences? =
            this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)


        binding.button2.setOnClickListener {
            preferences!!.edit {
                putBoolean("connected",false)
                putString("email","")
                putString("usersId" ,"")
            }
            findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailToSignInFragment())

        }

        binding.textView3.setText(preferences!!.getString("usersId",""));


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

