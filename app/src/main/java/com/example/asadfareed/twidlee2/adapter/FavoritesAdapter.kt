package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.fragments.restaurant.RestaurantFragment
import com.example.asadfareed.twidlee2.glidemodule.GlideApp
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.model.Restaurant
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_signup.view.*
import kotlinx.android.synthetic.main.item_list_deal.view.*
import kotlinx.android.synthetic.main.item_list_deal.view.counter
import kotlinx.android.synthetic.main.item_list_deal.view.dealOffer
import kotlinx.android.synthetic.main.item_list_deal.view.dealRating
import kotlinx.android.synthetic.main.item_list_deal.view.restaurantAddress
import kotlinx.android.synthetic.main.item_list_deal.view.restaurantCuisines
import kotlinx.android.synthetic.main.item_list_deal.view.restaurantName
import kotlinx.android.synthetic.main.item_list_favorites.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FavoritesAdapter(dealsList: ArrayList<Deal>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
      var dealsList1= dealsList

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_favorites, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindItems(dealsList1,position)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dealsList1.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(dealsList1: ArrayList<Deal>, position: Int) {
            utils.loadImage(itemView.context as FragmentActivity,itemView.thumbnail,
                R.drawable.restaurant_header_placeholder,dealsList1.get(position).cover_image)
            val cuisines = getCuisines(dealsList1, position)
            setViewsData(dealsList1, position, cuisines)
        }

        private fun setViewsData(
            dealsList1: ArrayList<Deal>,
            position: Int,
            cuisines: StringBuilder
        ) {
            itemView.restaurantNameFav.text = dealsList1.get(position).restaurant_name
            itemView.restaurantAddressFav.text = dealsList1.get(position).address.display_address
            itemView.restaurantCuisinesFav.text = cuisines
            itemView.dealOfferFav.text = dealsList1.get(position).title
           // itemView.dealTime.text = date + "-" + date2
            itemView.counterFav.text = dealsList1.get(position).table_time_limit.toString()
            itemView.markFavoriteFav.setImageResource(R.drawable.ic_heart_filled)
           // itemView.dealRating.rating = dealsList1.get(position).rating.toFloat()
            itemView.markFavoriteFav.setOnClickListener {

            }
        }

        private fun getCuisines(
            dealsList1: ArrayList<Deal>,
            position: Int
        ): StringBuilder {
            val sb = StringBuilder()
            val list:List<String> = dealsList1.get(position).cuisines
            for (i in 0 until list.size) {
                if (i<list.size-1){
                    sb.append(list[i]+ ",")
                }else {
                    sb.append(list[i])
                }
            }
            val cuisines = sb
            return cuisines
        }
    }

}