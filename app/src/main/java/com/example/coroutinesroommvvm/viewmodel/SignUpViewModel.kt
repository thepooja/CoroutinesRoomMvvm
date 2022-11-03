package com.example.coroutinesroommvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesroommvvm.model.LoginState
import com.example.coroutinesroommvvm.model.MyDatabase
import com.example.coroutinesroommvvm.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(application: Application):AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { MyDatabase(getApplication()).userDao() }
    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        coroutineScope.launch {
            val user = db.getUser(username)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = " Use already exists"
                }
            }
                else{
                    val user = User(username,password.hashCode(),info)
                    val  id = db.insertUser(user)
                    user.id = id
                    LoginState.login(user)
                    withContext(Dispatchers.Main){
                        signupComplete.value = true
                }
            }
        }
    }
}