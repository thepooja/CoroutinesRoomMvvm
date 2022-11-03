package com.example.coroutinesroommvvm.view

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
import com.example.coroutinesroommvvm.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_signup.*


class SignupFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupBtn.setOnClickListener { onSignup(it) }
        gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            Toast.makeText(activity, "Signup complete", Toast.LENGTH_SHORT).show()
            val action = SignupFragmentDirections.actionToMainFragment()
            Navigation.findNavController(signupUsername).navigate(action)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onSignup(v: View){
        val username = signupUsername.text.toString()
        val password = signupPassword.text.toString()
        val info = otherInfo.text.toString()
        if(username.isNullOrEmpty() || password.isNullOrEmpty() || info.isNullOrEmpty()){
            Toast.makeText(activity, "Please enter all fields", Toast.LENGTH_SHORT).show()
        }
        else{
            viewModel.signup(username, password, info)
        }
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionToLoginFragment()
        Navigation.findNavController(v).navigate(action)
    }

}