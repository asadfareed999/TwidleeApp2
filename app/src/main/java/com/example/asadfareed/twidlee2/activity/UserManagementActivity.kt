package com.example.asadfareed.twidlee2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asadfareed.twidlee2.MainActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.fragments.LoginFragment
import com.example.asadfareed.twidlee2.utils.utils

class UserManagementActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        utils.loadFragment(LoginFragment(), this)
    }


}
