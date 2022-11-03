package com.example.coroutinesroommvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesroommvvm.model.LoginState
import com.example.coroutinesroommvvm.model.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class LoginViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { MyDatabase(getApplication()).userDao() }
    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        coroutineScope.launch {
            val user = db.getUser(username)

            if(user == null)
            {
                withContext(Dispatchers.Main){
                    error.value= "User Not Found"
                }
            }
            else
            {
                if (user.passwordHash == password.hashCode())
                {
                    LoginState.login(user)
                    withContext(Dispatchers.Main){
                        loginComplete.value = true
                    }
                }else{
                    withContext(Dispatchers.Main){
                        error.value = "Password incorrect"
                    }
                }



            }
        }

    }
}