package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.model.Login
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view:View= inflater.inflate(R.layout.fragment_login, container, false)
        initViews()
        clickHandlers(view)
        return  view
    }

    private fun clickHandlers(view: View) {
        // goto sign up page
        view.fragment_textView_signUp.setOnClickListener {
            utils.loadFragment(SignUpFragment(),activity)
        }
        // login button
        view.fragment_button_login.setOnClickListener {
            /*progressBar.isIndeterminate=true*/
            val email: String = et_email_login.text.toString()
            val password: String = et_password_login.text.toString()
            validateData(email, password)
        }
        // load fragment forgot password
        view.fragment_textView_forgotPassword.setOnClickListener {
            utils.loadFragment(ForgotPasswordFragment(),activity)
        }

    }

    private fun validateData(email: String, password: String) {
        if (email.equals("")) {
            et_email_login.error = "Enter Email"
        } else if (password.equals("")) {
            et_password_login.error = "Enter Password"
        } else {
            val login: Login = Login(email, password)
            viewModel.login(login, activity)
        }
    }

    private fun initViews() {
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
       /* progressBar= activity!!.user_mngt_progressBar*/
    }

}