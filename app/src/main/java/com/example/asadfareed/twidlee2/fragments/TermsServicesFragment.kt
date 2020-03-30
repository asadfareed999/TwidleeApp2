package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.asadfareed.twidlee2.R
import kotlinx.android.synthetic.main.fragment_termservices_privacy.view.*
import kotlinx.android.synthetic.main.fragment_termservices_privacy.view.toolbar
import kotlinx.android.synthetic.main.toolbar_simple.view.*

class TermsServicesFragment(string: String, s1: String) : Fragment() {
    private val toolbarTitle:String=s1
    private val text:String=string

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_termservices_privacy, container, false)
        val view2:View=view.toolbar
        view2.toolbar_title.text=toolbarTitle
        view2.toolbar_title.visibility=View.VISIBLE
        view.textViewTermsPrivacy.text=text
        return view
    }
}