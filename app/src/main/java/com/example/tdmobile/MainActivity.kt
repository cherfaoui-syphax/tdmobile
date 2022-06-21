package com.example.tdmobile
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tdmobile.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val navController = this.findNavController(R.id.nav_host_fragment)
        // Find reference to bottom navigation view
         val navView = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)



    }


}
