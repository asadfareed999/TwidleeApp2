package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Menu
import com.example.asadfareed.twidlee2.model.Rating_summary
import kotlinx.android.synthetic.main.item_list_menu.view.*
import kotlinx.android.synthetic.main.item_list_menu_header.view.*


class MenuAdapter(
    menuDetails: Menu,
    array: IntArray,
    size: Int
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    var menu=menuDetails
    var indexArray=array
    var totalView=size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==0) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_menu_header, parent, false)
            return ViewHolder(v)
        }else {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_menu, parent, false)
            return ViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(menu,position,indexArray)
    }

    override fun getItemViewType(position: Int): Int {
        var type:Int=0
        if (position in indexArray){
            type=0
        }else{
            type= 1
        }
        return type
    }

    override fun getItemCount(): Int {
        return totalView
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        lateinit var ratings: Rating_summary

        fun bindItems(
            menuDetails: Menu,
            position: Int,
            indexArray: IntArray
        ) {
            if (adapterPosition in indexArray){
                val index:Int=indexArray.indexOf(adapterPosition)
                itemView.restaurantMenuCategory.text= menuDetails.menu_categories.get(index).name
            }else{
                var index:Int=1234444
                var size:Int=0
                for (i in 0 until menuDetails.menu_categories.size){
                    if (adapterPosition>size+menuDetails.menu_categories.get(i).menu_items.size){
                        size=size+menuDetails.menu_categories.get(i).menu_items.size+1
                    }else{
                        index=i
                        break
                    }
                }
                if (size==0) {
                    itemView.restaurantDishPrice.text =
                        menuDetails.menu_categories.get(index).menu_items.get(adapterPosition - 1)
                            .price.toString().removeSuffix(".0")
                    itemView.restaurantDishName.text=menuDetails.menu_categories.get(index).menu_items.get(adapterPosition-1).title+
                            "\n"+menuDetails.menu_categories.get(index).menu_items.get(adapterPosition-1).description

                }else{
                        itemView.restaurantDishPrice.text =
                            menuDetails.menu_categories.get(index).menu_items.get(adapterPosition - size-1)
                                .price.toString().removeSuffix(".0")
                        itemView.restaurantDishName.text =
                            menuDetails.menu_categories.get(index).menu_items.get(adapterPosition - size-1).title +
                                    "\n" + menuDetails.menu_categories.get(index).menu_items.get(
                                adapterPosition - size-1
                            ).description

                }
            }
        }



    }
}