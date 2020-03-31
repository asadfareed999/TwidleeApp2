package com.example.asadfareed.twidlee2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.asadfareed.twidlee2.MainActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.fragments.LoginFragment

class UserManagementActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
       // loadingBar.visibility=View.VISIBLE
        sharedPref= getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedEmailValue:String = sharedPref.getString("email_key","none")!!
        val sharedPasswordValue:String = sharedPref.getString("name_key","none")!!
        if (sharedEmailValue.equals("none") || sharedPasswordValue.equals("none")) {
            loadFragment(LoginFragment())
        }else{
            startActivity(Intent(this,
                MainActivity::class.java))
            finish()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
