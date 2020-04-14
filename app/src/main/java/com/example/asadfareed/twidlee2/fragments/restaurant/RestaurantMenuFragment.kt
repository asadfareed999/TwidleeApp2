package com.example.asadfareed.twidlee2.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.MenuAdapter
import com.example.asadfareed.twidlee2.model.Menu
import kotlinx.android.synthetic.main.fragment_restaurant_menu.view.*

class RestaurantMenuFragment(menu: Menu) : Fragment() {

    private val menuDetails=menu
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant_menu, container, false)
        setUpRecyclerview(view)
        return view
    }

    private fun setUpRecyclerview(view: View) {
        recyclerView = view.recyclerViewRestaurantMenu
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = MenuAdapter(menuDetails)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}