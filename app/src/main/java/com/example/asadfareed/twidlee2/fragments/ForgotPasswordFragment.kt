package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.GetCode
import com.example.asadfareed.twidlee2.model.UpdatePassword
import com.example.asadfareed.twidlee2.utils
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.view.*

class ForgotPasswordFragment : Fragment() {

    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        clickHandlers(view)
        return view
    }

    private fun clickHandlers(view: View) {
        view.fragment_textView_signUp.setOnClickListener {
            utils.loadFragment(
                SignUpFragment(),
                activity
            )
        }
        view.buttonSendConfirmForgotPassword.setOnClickListener {
            if (view.buttonSendConfirmForgotPassword.text.equals(getString(R.string.hint_send_code))) {
                getCode(view)
            } else if (view.buttonSendConfirmForgotPassword.text.equals(getString(R.string.hint_confirm))) {
                newPasswordValidation(view)

            }
        }
    }

    private fun getCode(view: View) {
        if (view.phone_number_profile.text.toString().isEmpty()) {
            view.phone_number_profile.error = "Enter a valid phone number"
        } else {
            val phone: String =
                view.ccp.selectedCountryCodeWithPlus + view.phone_number_profile.text.toString()
            val getCode: GetCode =
                GetCode(phone)
            viewModel.getCode(activity!!, getCode, view)
        }
    }

    private fun newPasswordValidation(view: View) {
        val code: String = view.verificationCodeForgotPassword.editText!!.text.toString()
        val newPassword: String = view.newPasswordForgotPassword.editText!!.text.toString()
        val confirmNewPassword: String =
            view.confirmPasswordForgotPassword.editText!!.text.toString()
        if (code.length != 4) {
            view.verificationCodeForgotPassword.error = "Enter 4 digit code"
        } else if (newPassword.length < 5) {
            view.verificationCodeForgotPassword.isErrorEnabled = false
            view.newPasswordForgotPassword.error = "Enter at least 5 characters"
        } else if (!newPassword.equals(confirmNewPassword)) {
            view.verificationCodeForgotPassword.isErrorEnabled = false
            view.newPasswordForgotPassword.isErrorEnabled = false
            view.confirmPasswordForgotPassword.error = "Password did not match"
        } else {
            view.verificationCodeForgotPassword.isErrorEnabled = false
            view.newPasswordForgotPassword.isErrorEnabled = false
            view.confirmPasswordForgotPassword.isErrorEnabled = false
            val updatePassword: UpdatePassword = UpdatePassword(newPassword, code)
            viewModel.updatePassword(activity, updatePassword)
        }
    }
}