package com.example.asadfareed.twidlee2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.asadfareed.twidlee2.fragments.AccountFragment
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(DealsFragment())
       // bottom_navigation_view.visibility=View.GONE
        navItemsHandler()
    }

    private fun navItemsHandler() {
        bottom_navigation_view.setOnNavigationItemSelectedListener {
           // selectedItemIdCustom=it.itemId
            when (it.itemId) {
                R.id.navigation_home -> {
                   loadFragment(DealsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
               /* R.id.reservations -> {
                    loadFragment(ReservationsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    loadFragment(HistoryFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorites -> {
                    loadFragment(FavoritesFragment())
                    return@setOnNavigationItemSelectedListener true
                }*/
                R.id.navigation_account -> {
                    loadFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /*override fun onBackPressed() {
        when(selectedItemIdCustom){
            R.id.account -> {
                loadFragment(FavoritesFragment())
                selectedItemIdCustom = R.id.favorites
                navigation.selectedItemId=selectedItemIdCustom
            }
            R.id.favorites -> {
                loadFragment(HistoryFragment())
                selectedItemIdCustom = R.id.history
                navigation.selectedItemId=selectedItemIdCustom
            }
            R.id.history -> {
                loadFragment(ReservationsFragment())
                selectedItemIdCustom = R.id.reservations
                navigation.selectedItemId=selectedItemIdCustom
            }
            R.id.reservations -> {
                loadFragment(HomeFragment())
                selectedItemIdCustom = R.id.home
                navigation.selectedItemId=selectedItemIdCustom
            }
            else -> {
                *//*super.onBackPressed()*//*
                finish()
            }
        }
    }*/
}
