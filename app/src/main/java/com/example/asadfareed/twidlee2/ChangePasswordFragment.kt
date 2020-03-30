package com.example.asadfareed.twidlee2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import kotlinx.android.synthetic.main.toolbar_simple.view.toolbar

class ChangePasswordFragment(s: String) : Fragment() {

    private var title:String=s

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_change_password, container, false)
        view.toolbar.toolbar_title.text=title
        view.toolbar.toolbar_title.visibility=View.VISIBLE
        return view
    }
}