package com.example.movieexample

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(imgUrl:String) {
    Glide.with(context).load(imgUrl).into(this)
}

fun isInternetAvailable(context: Context): Boolean {
    var isConnected: Boolean = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    Log.d("connected",isConnected.toString());
    return isConnected
}
