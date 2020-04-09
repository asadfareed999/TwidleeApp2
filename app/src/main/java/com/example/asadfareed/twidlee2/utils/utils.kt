package com.example.asadfareed.twidlee2.utils

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.glidemodule.GlideApp

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
    }
}