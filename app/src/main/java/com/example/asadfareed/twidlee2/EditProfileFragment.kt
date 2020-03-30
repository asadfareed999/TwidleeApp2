package com.example.asadfareed.twidlee2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.model.UpdateProfile
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.toolbar

class EditProfileFragment(s: String) : Fragment()  {

    private lateinit var viewModel: ViewModel
    private val title:String=s

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        view.toolbar.toolbar_title.text=title
        view.toolbar.toolbar_title.visibility=View.VISIBLE
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        view.buttonEditSaveProfile.setOnClickListener {
            var nameOk:Boolean=false
            var phoneOk:Boolean=false
            val name:String= view.textInputLayoutNameProfile.editText!!.text.toString()
            val phone:String= view.fragment_textInputLayout_mobileNumber_profile.editText!!.text.toString()
            if (name.length>0 && name.length<3){
                view.textInputLayoutNameProfile.error="Enter at least 3 charaters"
            }else if (name.length>0 && name.length>=3){
                view.textInputLayoutNameProfile.isErrorEnabled=false
                nameOk=true
            }
            if (!phone.isEmpty()){
                phoneOk=true
            }
            if (nameOk==true && phoneOk==false){
                val updateProfile:UpdateProfile= UpdateProfile(name=name)
                viewModel.updateProfile(activity,updateProfile)
            }
        }
        return view
        }
}