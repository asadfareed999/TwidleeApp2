package com.example.asadfareed.twidlee2.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.RestaurantDealsAdapter
import com.example.asadfareed.twidlee2.model.Deals
import kotlinx.android.synthetic.main.fragment_restaurant_deals.view.*

class RestaurantDealsFragment(deals: List<Deals>) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantDealsAdapter
    private val dealsRestaurant=deals


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant_deals, container, false)
        recyclerView = view.fragmentDealListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = RestaurantDealsAdapter(dealsRestaurant)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        return view
    }
}