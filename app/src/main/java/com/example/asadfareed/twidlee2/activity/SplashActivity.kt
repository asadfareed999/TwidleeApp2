package com.example.asadfareed.twidlee2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.asadfareed.twidlee2.GlideApp
import com.example.asadfareed.twidlee2.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLASH_SCREEN : Long = 8000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        setData()
        startingActivity()
    }

    private fun startingActivity() {
        Handler().postDelayed(Runnable {
            val intent = Intent(this, UserManagementActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN)
    }

    private fun setData() {
        GlideApp.with(this)
            .load(R.drawable.twidlee_animation)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_background)
            .into(splash_animation)
    }

}
