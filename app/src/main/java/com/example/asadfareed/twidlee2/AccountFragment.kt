package com.example.asadfareed.twidlee2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.fragments.TermsServicesFragment
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)
        fetchData(view)
        clickHandlers(view)
        return view
    }

    private fun clickHandlers(view: View) {

        view.textViewLogout.setOnClickListener {
            viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
            viewModel.logout(activity!!)

        }
        view.textViewChangePassword.setOnClickListener {
            loadFragment(ChangePasswordFragment("Change Password"))
        }
        view.textViewTerms.setOnClickListener {
            loadFragment(TermsServicesFragment(getString(R.string.text_terms_services),
                    "Terms & Conditions"))
        }
        view.textViewPrivacy.setOnClickListener {
            loadFragment(
                TermsServicesFragment(getString(R.string.text_privacy_policy),
                    "Privacy Policy"))
        }
        view.textViewAbout.setOnClickListener {
            loadFragment(
                TermsServicesFragment(getString(R.string.text_about_us), "About Us"))
        }
        view.textViewProfile.setOnClickListener {
            loadFragment(EditProfileFragment("Profile"))
        }
    }

    private fun fetchData(view: View) {
        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val Name: String = sharedPref.getString("name_key", "Name")!!
        val sharedPushNotifyValue: Boolean = sharedPref.getBoolean("is_push_notification_key", true)
        val sharedEmailNotifyValue: Boolean =
            sharedPref.getBoolean("is_email_notification_key", true)
        view.textViewName.text = Name
        setSwitch(sharedPushNotifyValue, view, sharedEmailNotifyValue)
    }

    private fun setSwitch(sharedPushNotifyValue: Boolean, view: View, sharedEmailNotifyValue: Boolean) {
        view.switchPushNotification.isChecked = sharedPushNotifyValue
        view.switchEmailNotification.isChecked = sharedEmailNotifyValue
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
