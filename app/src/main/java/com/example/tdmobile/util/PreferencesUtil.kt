package com.example.menuapplication

import android.content.Context
import androidx.core.content.edit

const val fileName = "pref"
const val invalidUserId = -1

fun saveUserID(context: Context,userId:Int) {
    val pref = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
    pref.edit {
        putInt("userId",userId)
    }

}

fun getUserId(context: Context):Int {
    val pref = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
    return pref.getInt("userId",invalidUserId)
}

fun isLogin(context: Context)= getUserId(context)!=invalidUserId

fun clearUserId(context: Context) {
   saveUserID(context,invalidUserId)
}