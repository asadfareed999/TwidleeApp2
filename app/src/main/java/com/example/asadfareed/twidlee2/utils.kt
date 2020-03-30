package com.example.asadfareed.twidlee2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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