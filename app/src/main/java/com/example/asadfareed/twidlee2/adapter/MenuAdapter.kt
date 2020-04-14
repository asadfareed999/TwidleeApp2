package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Menu
import kotlinx.android.synthetic.main.item_list_nested_recycler.view.*


class MenuAdapter(menuDetails: Menu) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    var menu=menuDetails

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_nested_recycler, parent, false)
            return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(menu,position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return menu.menu_categories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: MenuNestedAdapter

        fun bindItems(menuDetails: Menu,position: Int) {
            setUpRecyclerview(menuDetails,position)
        }
        private fun setUpRecyclerview(
            menuDetails: Menu,
            position: Int
        ) {
            recyclerView = itemView.nestedRecyclerViewRestaurantMenu
            recyclerView.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            adapter = MenuNestedAdapter(menuDetails.menu_categories.get(position))
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}