package com.example.coroutinesroommvvm.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.coroutinesroommvvm.R
import com.example.coroutinesroommvvm.model.LoginState
import com.example.coroutinesroommvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameTV.text = LoginState.user?.username
        signoutBtn.setOnClickListener { onSignout() }
        deleteUserBtn.setOnClickListener { onDelete() }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.signout.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "Signed out", Toast.LENGTH_SHORT).show()
            gotoSignUpScreen()
        })
        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "User Deleted", Toast.LENGTH_SHORT).show()
            gotoSignUpScreen()
        })
    }

    fun gotoSignUpScreen(){
        val action = MainFragmentDirections.actionGoToSignUp()
        Navigation.findNavController(usernameTV).navigate(action)
    }
    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete() {
        activity.let {
            AlertDialog.Builder(it)
                .setTitle("Delete User")
                .setMessage("Are you sure want to delete this user?")
                .setPositiveButton("yes"){ p0,p1 -> viewModel.onDeleteUser() }
                .setNegativeButton("Cancel",null)
                .create().show()
        }
    }

}