package com.example.asadfareed.twidlee2.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.glidemodule.GlideApp
import com.example.asadfareed.twidlee2.model.GetCode
import com.example.asadfareed.twidlee2.model.UpdateProfile
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.toolbar
import java.io.ByteArrayOutputStream
import java.io.InputStream


class EditProfileFragment(s: String) : Fragment()  {

    private val REQUEST_CAMERA: Int=113
    private val SELECT_FILE: Int=112
    private lateinit var userChoosenTask: String
    private lateinit var viewModel: ViewModel
    private val title:String=s
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var Name: String
    private lateinit var Phone: String
    private lateinit var ProfilePic: String


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
        //activity!!.bottom_navigation_view.visibility=View.GONE
        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        Name = sharedPref.getString("name_key", "Name")!!
        Phone= sharedPref.getString("phone_key", "").toString()
        ProfilePic= sharedPref.getString("profile_picture_key", "").toString()
        if (!ProfilePic.isEmpty()) {
            utils.loadImage(view.context as FragmentActivity,view.imageViewProfilePicProfile,
                R.drawable.ic_placeholder_avatar,ProfilePic)
        }
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
        view.imageViewProfilePicProfile.setOnClickListener {
            selectImage(view)
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
                if (!codeField.text.toString().equals(data)){
                    view.buttonEditSaveProfile.setBackgroundResource(R.drawable.button_background)
                    view.buttonEditSaveProfile.isEnabled=true
                }else if (codeField.text.toString().equals(data)){
                    view.buttonEditSaveProfile.setBackgroundResource(R.drawable.button_disable_background)
                    view.buttonEditSaveProfile.isEnabled=false
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                        }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun selectImage(view: View) {
        val items = arrayOf<CharSequence>(
            "Take Photo", "Choose from Library",
            "Cancel"
        )
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context as FragmentActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
           // val result: Boolean = Utility.checkPermission(view.context as FragmentActivity)
            if (items[item] == "Take Photo") {
                userChoosenTask = "Take Photo"
                //if (result) cameraIntent()
                cameraIntent()
            } else if (items[item] == "Choose from Library") {
                userChoosenTask = "Choose from Library"
                //if (result) galleryIntent()
                galleryIntent()
            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
    }

    private fun cameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                val imageUri: Uri? = data!!.data
                var imageStream: InputStream? = null
                    imageStream = this.context!!.getContentResolver().openInputStream(imageUri!!)
                val yourSelectedImage = BitmapFactory.decodeStream(imageStream)
              val imageURL = encodeTobase64(yourSelectedImage)
                val updateProfile=UpdateProfile(profile_picture = imageURL)
                viewModel.updateProfile(this.context as FragmentActivity,updateProfile)
            }
            else if (requestCode == REQUEST_CAMERA) {
                val imageUri: Uri? = data!!.data
                var imageStream: InputStream? = null
                imageStream = this.context!!.getContentResolver().openInputStream(imageUri!!)
                val yourSelectedImage = BitmapFactory.decodeStream(imageStream)
               val imageURL = encodeTobase64(yourSelectedImage)
                val updateProfile=UpdateProfile(profile_picture = imageURL)
                viewModel.updateProfile(this.context as FragmentActivity,updateProfile)
            }
        }
    }

    fun encodeTobase64(image: Bitmap): String? {
        val immagex: Bitmap = image
        val baos = ByteArrayOutputStream()
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        Log.e("LOOK", imageEncoded)
        return imageEncoded
    }

}