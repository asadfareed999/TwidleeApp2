package com.example.asadfareed.twidlee2.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.UpdateProfile
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_account.view.*


class AccountFragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var updateProfile: UpdateProfile

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        fetchData(view)
        clickHandlers(view)
        return view
    }

    private fun clickHandlers(view: View) {

        view.textViewLogout.setOnClickListener {
            viewModel.logout(activity!!)
        }
        view.textViewChangePassword.setOnClickListener {
           /* val action =AccountFragmentDirections.actionNavigationChangePassword()
           action.set(name)
           NavHostFragment.findNavController(this).navigate(action)
            if (findNavController().currentDestination?.id == R.id.navigation_account) {
                findNavController().navigate(action)}*/
            loadFragment(
                ChangePasswordFragment(
                    "Change Password"
                )
            )
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
            loadFragment(
                EditProfileFragment(
                    "Profile"
                )
            )
        }
        switchPushNotificationsHandler(view)
        switchEmailNotificationHandler(view)
    }

    private fun switchEmailNotificationHandler(view: View) {
        view.switchEmailNotification.setOnClickListener {
            if (view.switchEmailNotification.isChecked) {
                updateProfile = UpdateProfile(is_email_notification = true)
            } else {
                updateProfile = UpdateProfile(is_email_notification = false)
            }
            viewModel.updateProfile(activity, updateProfile)
        }
    }

    private fun switchPushNotificationsHandler(view: View) {
        view.switchPushNotification.setOnClickListener {
            if (view.switchPushNotification.isChecked) {
                updateProfile = UpdateProfile(is_push_notification = true)
            } else {
                updateProfile = UpdateProfile(is_push_notification = false)
            }
            viewModel.updateProfile(activity, updateProfile)
        }
    }

    private fun fetchData(view: View) {
        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var Name: String = sharedPref.getString("name_key", "Name")!!
        val Email: String = sharedPref.getString("email_key", "email")!!
        val Phone: String = sharedPref.getString("phone_key", "phone")!!
        val sharedPushNotifyValue: Boolean = sharedPref.getBoolean("is_push_notification_key", true)
        val sharedEmailNotifyValue: Boolean =
            sharedPref.getBoolean("is_email_notification_key", true)
        setData(Name, Email, Phone, view, sharedPushNotifyValue, sharedEmailNotifyValue)
    }

    private fun setData(
        Name: String,
        Email: String,
        Phone: String,
        view: View,
        sharedPushNotifyValue: Boolean,
        sharedEmailNotifyValue: Boolean
    ) {
        var ss1 = SpannableString(Name)
        ss1.setSpan(RelativeSizeSpan(1.3f), 0, ss1.length, 0) // set size
        ss1.setSpan(ForegroundColorSpan(Color.RED), 0, ss1.length, 0) // set color
        ss1.setSpan(StyleSpan(Typeface.BOLD), 0, ss1.length, 0) // set style
        val text = TextUtils.concat(ss1, "\n" + Email + "\n" + Phone)
        view.textViewName.text = text
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


