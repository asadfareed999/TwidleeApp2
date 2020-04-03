package com.example.asadfareed.twidlee2.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.GetCode
import com.example.asadfareed.twidlee2.model.UpdateProfile
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.toolbar

class EditProfileFragment(s: String) : Fragment()  {

    private lateinit var viewModel: ViewModel
    private val title:String=s
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val (Name: String, Phone: String) = setUpData(view)
        clickHandlers(view, Name, Phone)
        return view
        }

    private fun setUpData(view: View): Pair<String, String> {
        view.toolbar.toolbar_title.text = title
        view.toolbar.toolbar_title.visibility = View.VISIBLE
        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val Name: String = sharedPref.getString("name_key", "Name")!!
        val Phone: String = sharedPref.getString("phone_key", "").toString()
        view.et_name_editProfile.setText(Name)
        view.fragment_textInputLayout_mobileNumber_profile.editText!!.setText(Phone.substring(3))
        enableButton(view.et_name_editProfile,Name,view)
        enableButton(view.fragment_textInputLayout_mobileNumber_profile.editText!!,Phone.substring(3),view)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        return Pair(Name, Phone)
    }

    private fun clickHandlers(view: View, Name: String, Phone: String) {
        view.buttonEditSaveProfile.setOnClickListener {
            var nameOk: Boolean
            var phoneOk: Boolean
            val name: String = view.textInputLayoutNameProfile.editText!!.text.toString()
            val phone: String =
                view.ccp.selectedCountryCodeWithPlus + view.fragment_textInputLayout_mobileNumber_profile.editText!!.text.toString()
            nameOk = !name.equals(Name)
            phoneOk = !phone.equals(Phone)
            if (view.buttonEditSaveProfile.text.equals("Update Profile")) {
                val code: String = view.profileVerificationCode.editText?.text.toString()
                val updateProfile: UpdateProfile =
                    UpdateProfile(phone = phone, code = code, name = name)
                viewModel.updateProfile(activity, updateProfile)
            } else {
                updateNameAndNumber(nameOk, phoneOk, name, phone, view)
            }
        }
    }

    private fun updateNameAndNumber(
        nameOk: Boolean,
        phoneOk: Boolean,
        name: String,
        phone: String,
        view: View
    ) {
        if (nameOk == true && phoneOk == false) {
            val updateProfile: UpdateProfile = UpdateProfile(name = name)
            viewModel.updateProfile(activity, updateProfile)
        } else if (nameOk == false && phoneOk == true) {
            val getCode: GetCode = GetCode(phone)
            viewModel.getCodeUpdatePhone(activity!!, getCode, view)
        } else if (nameOk == true && phoneOk == true) {
            val getCode: GetCode = GetCode(phone)
            viewModel.getCodeUpdatePhone(activity!!, getCode, view)
        } else {
            Toast.makeText(activity, "No changes made ", Toast.LENGTH_LONG).show()
        }
    }

    private fun enableButton(
        codeField: EditText,
        data: String,
        view: View
    ) {
        codeField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val dat=data
                if (!s.equals(data)) //size as per your requirement
                {
                    view.buttonEditSaveProfile.setBackgroundResource(R.drawable.button_background)
                    view.buttonEditSaveProfile.isEnabled=true
                }else if (s.equals(data)){
                    view.buttonEditSaveProfile.setBackgroundResource(R.drawable.button_disable_background)
                    view.buttonEditSaveProfile.isEnabled=false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                          }
        })
    }
}