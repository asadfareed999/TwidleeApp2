package com.example.asadfareed.twidlee2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.asadfareed.twidlee2.CodeVerificationFragment
import com.example.asadfareed.twidlee2.MainActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.fragments.LoginFragment
import com.example.asadfareed.twidlee2.utils

class UserManagementActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        val (sharedEmailValue: String, sharednameValue: String, sharedPhoneStatusValue: Boolean)
                = fetchSharedPrefData()
        dataValidation(sharedEmailValue, sharednameValue, sharedPhoneStatusValue)
    }

    private fun fetchSharedPrefData(): Triple<String, String, Boolean> {
        sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedEmailValue: String = sharedPref.getString("email_key", "none")!!
        val sharednameValue: String = sharedPref.getString("name_key", "none")!!
        val sharedPhoneStatusValue: Boolean = sharedPref.getBoolean("phone_verified_key", false)
        return Triple(sharedEmailValue, sharednameValue, sharedPhoneStatusValue)
    }

    private fun dataValidation(
        sharedEmailValue: String,
        sharednameValue: String,
        sharedPhoneStatusValue: Boolean
    ) {
        if (sharedEmailValue.equals("none") || sharednameValue.equals("none")
            || sharedPhoneStatusValue.equals(false)
        ) {
            utils.loadFragment(LoginFragment(), this)
        } else {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finish()
        }
    }

}
