package com.example.asadfareed.twidlee2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.asadfareed.twidlee2.MainActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.glidemodule.GlideApp
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLASH_SCREEN : Long = 8000
    private lateinit var handler:Handler
    private lateinit var runnable: Runnable
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var intentStart: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_splash)
        setData()
        startingActivity()
    }

    private fun fullScreen() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun startingActivity() {
        handler.postDelayed(runnable,SPLASH_SCREEN)
    }

    private fun setData() {
        GlideApp.with(this)
            .load(R.drawable.twidlee_animation)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_background)
            .into(splash_animation)
        val token = fetchSharedPrefData()
        dataValidation(token)
        handler= Handler()
        runnable = Runnable{
            startActivity(intentStart)
            finish()
        }
    }

    private fun fetchSharedPrefData(): String {
        sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedTokenValue: String = sharedPref.getString("token_key", "")!!
        return sharedTokenValue
    }

    private fun dataValidation(
        sharedtokenValue: String
    ) {
        if (sharedtokenValue.isEmpty()) {
             intentStart = Intent(this, UserManagementActivity::class.java)
        } else {
            intentStart = Intent(this, MainActivity::class.java)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        handler.removeCallbacksAndMessages(null)
        finish()
    }
}
