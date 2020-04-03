package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.SignUp
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.view.*

class SignUpFragment: Fragment() {

    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view:View= inflater.inflate(R.layout.fragment_signup, container, false)
        initViews()
        clickHandlers(view)
        return  view
    }

    private fun clickHandlers(view: View) {
        // go to login page
        view.fragment_textView_login.setOnClickListener {
            utils.loadFragment(LoginFragment(),activity)
        }
        // create account button handler
        view.fragment_button_signUp.setOnClickListener {
            signUp()
        }
    }

    private fun initViews() {
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
    }

    private fun signUp() {
        val name: String = et_name_signUp.text.toString()
        val email: String = et_email_signUp.text.toString()
        val phone: String = ccp.selectedCountryCodeWithPlus + phone_number_edt.text.toString()
        val password: String = et_password_signUp.text.toString()
        validateData(name, email, password, phone)
    }

    private fun validateData(name: String, email: String, password: String, phone: String) {
        if (name.length<3) {
            et_name_signUp.error = "Enter Name of at least 3 character"
        } else if (email.isEmpty()) {
            et_email_signUp.error = "Enter Email"
        } else if (phone_number_edt.text.toString().isEmpty()) {
            phone_number_edt.error = "Enter Phone Number"
        } else if (password.length<5) {
            et_password_signUp.error = "Enter at least 5 character password"
        } else {
           // Toast.makeText(activity, "" + name + email + phone + password, Toast.LENGTH_LONG).show()
            val signUp: SignUp = SignUp(email, password, phone, name)
            viewModel.signUp(signUp, activity)
        }
    }


}