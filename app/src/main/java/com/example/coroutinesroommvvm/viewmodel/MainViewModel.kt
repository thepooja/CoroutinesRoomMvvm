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

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { MyDatabase(getApplication()).userDao() }
    val userDeleted = MutableLiveData<Boolean>()
    val signout = MutableLiveData<Boolean>()

    fun onSignout() {
        LoginState.logout()
        signout.value = true
    }

    fun onDeleteUser() {
        coroutineScope.launch {
            LoginState.user?.let { user -> db.deleteUser(user.id)}
            withContext(Dispatchers.Main){
                LoginState.logout()
                userDeleted.value = true
            }
        }
    }
}