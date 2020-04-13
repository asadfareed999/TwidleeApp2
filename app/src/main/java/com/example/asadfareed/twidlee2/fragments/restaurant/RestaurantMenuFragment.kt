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
        val headers:Int=menuDetails.menu_categories.size
        val array = IntArray(menuDetails.menu_categories.size)
        var size:Int = menuDetails.menu_categories.get(0).menu_items.size
        array[0]=0
        for (i in 1 until headers){
            array[i]=array[i-1]+menuDetails.menu_categories.get(i-1).menu_items.size+1
            size=size+menuDetails.menu_categories.get(i).menu_items.size
        }
        size=size+array.size
        setUpRecyclerview(view,array,size)
        return view
    }

    private fun setUpRecyclerview(
        view: View,
        array: IntArray,
        size: Int
    ) {
        recyclerView = view.recyclerViewRestaurantMenu
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = MenuAdapter(menuDetails,array,size)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}