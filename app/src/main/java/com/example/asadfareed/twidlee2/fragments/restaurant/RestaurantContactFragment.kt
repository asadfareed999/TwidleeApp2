package com.example.asadfareed.twidlee2.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.ChangePassword
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.toolbar

class RestaurantContactFragment() : Fragment() {

    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant_contact, container, false)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        return view
    }
}