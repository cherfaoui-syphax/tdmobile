package com.example.tdmobile.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.menuapplication.User
import com.example.menuapplication.clearUserId
import com.example.menuapplication.saveUserID


class UserModel():ViewModel() {

var user = MutableLiveData<User?>()
var message = MutableLiveData<String>()


     fun login(context:Context,username: String, password: String) {
        if(username=="test@gmail.com" && password =="test") {
            saveUserID(context,12)
            user.postValue(User(userId = 12))
        }
        else {
            message.value = "Email ou mot de passe erooné"
        }

    }

    fun logout(context: Context) {
        clearUserId(context)
        user.postValue(null)



    }
}