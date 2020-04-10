package com.example.asadfareed.twidlee2.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.BusinessHoursAdapter
import com.example.asadfareed.twidlee2.model.Restaurant
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.fragment_restaurant_contact.view.*

class RestaurantContactFragment(restaurantDetails: Restaurant) : Fragment() {

    private var restaurant=restaurantDetails
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BusinessHoursAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant_contact, container, false)
        setViewData(view)
        setUpRecyclerview(view)
        return view
    }

    private fun setViewData(view: View) {
        view.contactNumber1.text = utils.spannableStringBold(restaurant.address.business_contact)
        view.contactNumber2.text = utils.spannableStringBold(restaurant.address.business_contact_1)
        val text = view.context.getString(R.string.hint_restaurant_hours).toUpperCase()
        view.contactRestaurantHours.text = utils.spannableString(text, 1.3f)
    }

    private fun setUpRecyclerview(view: View) {
        recyclerView = view.contactBusinessHoursRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = BusinessHoursAdapter(restaurant.business_hours)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}