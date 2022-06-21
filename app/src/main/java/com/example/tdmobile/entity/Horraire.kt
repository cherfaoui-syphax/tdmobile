package com.example.tdmobile.entity

import com.google.gson.annotations.SerializedName

data class Horraire (
    @SerializedName("f1")
    val jour : Int,
    @SerializedName("f2")
    val ouverture : String,
    @SerializedName("f3")
    val frmeture : String,

        )
