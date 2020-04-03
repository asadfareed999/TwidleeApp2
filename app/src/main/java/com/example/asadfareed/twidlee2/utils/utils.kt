package com.example.asadfareed.twidlee2.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.twidlee2.R

class utils {
    companion object {
         fun loadFragment(fragment: Fragment, activity: FragmentActivity?) {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}