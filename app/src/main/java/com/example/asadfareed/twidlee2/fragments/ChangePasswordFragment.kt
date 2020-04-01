package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.ChangePassword
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.toolbar

class ChangePasswordFragment(s: String) : Fragment() {

    private lateinit var viewModel: ViewModel
    private var title:String=s
    private lateinit var editTextOld:EditText
    private lateinit var editTextNew:EditText
    private lateinit var editTextNewConfirm:EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_change_password, container, false)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        setUpData(view)
        clickHandlers(view)
        return view
    }

    private fun setUpData(view: View) {
        view.toolbar.toolbar_title.text = title
        view.toolbar.toolbar_title.visibility = View.VISIBLE
        editTextOld = view.changePasswordOld.editText!!
        editTextNew = view.changePasswordNew.editText!!
        editTextNewConfirm = view.changePasswordNewConfirm.editText!!
    }

    private fun clickHandlers(view: View) {
        view.buttonSaveChangePassword.setOnClickListener {
            val oldPassword: String = editTextOld.text.toString()
            val newPassword: String = editTextNew.text.toString()
            val newPasswordConfirm: String = editTextNewConfirm.text.toString()
            dataValidation(oldPassword, newPassword, view, newPasswordConfirm)
        }
    }

    private fun dataValidation(
        oldPassword: String,
        newPassword: String,
        view: View,
        newPasswordConfirm: String
    ) {
        if (oldPassword.isEmpty()) {
            editTextOld.error = "Please enter old password"
        } else if (newPassword.isEmpty()) {
            view.changePasswordOld.isErrorEnabled = false
            editTextNew.error = "please enter new password"
        } else if (newPassword.length < 5) {
            view.changePasswordOld.isErrorEnabled = false
            editTextNew.error = "please enter 5 character password"
        } else if (!newPasswordConfirm.equals(newPassword)) {
            view.changePasswordOld.isErrorEnabled = false
            editTextNewConfirm.error = "password did not match"
        } else {
            val changePassword: ChangePassword = ChangePassword(oldPassword, newPasswordConfirm)
            viewModel.changePassword(activity, changePassword)
        }
    }
}