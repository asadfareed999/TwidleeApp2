package com.example.asadfareed.twidlee2.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.activity.UserManagementActivity
import com.example.asadfareed.twidlee2.glidemodule.GlideApp
import java.text.SimpleDateFormat
import java.util.*

class utils {
    companion object {

         fun loadFragment(fragment: Fragment, activity: FragmentActivity?) {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        fun loadFragment2(fragment: Fragment, activity: FragmentActivity?) {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        fun loadImage(
            activity: FragmentActivity,
            restaurantHeaderThumbnail: ImageView,
            restaurantHeaderPlaceholder: Int,
            coverImage: String
        ) {
            GlideApp.with(activity)
                .load(coverImage)
                .fitCenter()
                .placeholder(restaurantHeaderPlaceholder)
                .into(restaurantHeaderThumbnail)
        }
        fun spannableStringColor(string: String): SpannableString {
            val ss1 = SpannableString(string)
            ss1.setSpan(ForegroundColorSpan(Color.RED), 0, ss1.length, 0) // set color
            return ss1
        }
        fun spannableStringBold(string: String): SpannableString {
            val ss1 = SpannableString(string)
            ss1.setSpan(StyleSpan(Typeface.BOLD), 0, ss1.length, 0) // set style
            return ss1
        }
        fun spannableStringSize(string: String,p:Float): SpannableString {
            val ss1 = SpannableString(string)
            ss1.setSpan(RelativeSizeSpan(p), 0, ss1.length, 0) // set size
            return ss1
        }
        fun spannableString(string: String,p:Float): SpannableString {
            val ss1 = SpannableString(string)
            ss1.setSpan(ForegroundColorSpan(Color.RED), 0, ss1.length, 0) // set color
            ss1.setSpan(StyleSpan(Typeface.BOLD), 0, ss1.length, 0) // set style
            ss1.setSpan(RelativeSizeSpan(p), 0, ss1.length, 0) // set size
            return ss1
        }

        fun formateDate(date: String): String {
            var date1 = date
            var spf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val newDate: Date = spf.parse(date1)
            spf = SimpleDateFormat("hh:mm aaa")
            date1 = spf.format(newDate)
            return date1
        }
        fun logOut(activity: FragmentActivity){
            val sharedPrefFile = "kotlinsharedpreference"
            lateinit var sharedPref: SharedPreferences
            sharedPref = activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            activity.startActivity(Intent(activity, UserManagementActivity::class.java))
            activity.finish()
        }
    }
}