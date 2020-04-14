package com.example.asadfareed.twidlee2.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Menu
import com.example.asadfareed.twidlee2.model.Menu_categories
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.item_list_menu.view.*
import kotlinx.android.synthetic.main.item_list_menu_header.view.*


class MenuNestedAdapter(category: Menu_categories) : RecyclerView.Adapter<MenuNestedAdapter.ViewHolder>() {

    var menuCategory=category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==0) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_menu_header, parent, false)
            return ViewHolder(v)
        }else{
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_menu, parent, false)
            return ViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(menuCategory,position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return menuCategory.menu_items.size+1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(menuCategory: Menu_categories,position: Int) {
            if (adapterPosition==0) {
                itemView.restaurantMenuCategory.text =
                    menuCategory.name
            }else {
                itemView.restaurantDishPrice.text =
                    menuCategory.menu_items.get(adapterPosition - 1)
                        .price.toString().removeSuffix(".0")
                val text=utils.spannableStringColor(menuCategory.menu_items.get(adapterPosition-1).title)
               val text2=TextUtils.concat(text,"\n")
                val text3=TextUtils.concat(text2,menuCategory.menu_items.get(adapterPosition-1).description)
                itemView.restaurantDishName.text=text3
            }
        }
    }
}