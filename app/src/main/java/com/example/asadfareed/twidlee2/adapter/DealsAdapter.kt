package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.GlideApp
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Deal
import kotlinx.android.synthetic.main.item_list_deal.view.*
import java.text.SimpleDateFormat
import java.util.*


class DealsAdapter(dealsList: ArrayList<Deal>) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {
      var dealsList1= dealsList

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_deal, parent, false)
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
        val imageView:ImageView=itemView.restaurantCoverImage
        fun bindItems(dealsList1: ArrayList<Deal>, position: Int) {
            loadImage(dealsList1, position)
            val cuisines = getCuisines(dealsList1, position)
            var (date, date2) = formatDates(dealsList1, position)
            setViewsData(dealsList1, position, cuisines, date, date2)
        }

        private fun getCuisines(
            dealsList1: ArrayList<Deal>,
            position: Int
        ): StringBuilder {
            val sb = StringBuilder()
            for (i in 1..dealsList1.get(position).cuisines.size) {
                sb.append(dealsList1.get(position).cuisines[i - 1] + ",")
            }
            val cuisines = sb
            return cuisines
        }

        private fun formatDates(
            dealsList1: ArrayList<Deal>,
            position: Int
        ): Pair<String, String> {
            var date = dealsList1.get(position).start_time
            var date2 = dealsList1.get(position).end_time
            // var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
            date = formateDate(date)
            date2 = formateDate(date2)
            return Pair(date, date2)
        }

        private fun setViewsData(
            dealsList1: ArrayList<Deal>,
            position: Int,
            cuisines: StringBuilder,
            date: String,
            date2: String
        ) {
            itemView.restaurantName.text = dealsList1.get(position).restaurant_name
            itemView.restaurantAddress.text = dealsList1.get(position).address.display_address
            itemView.restaurantCuisines.text = cuisines
            itemView.dealOffer.text = dealsList1.get(position).title
            itemView.dealTime.text = date + "-" + date2
            itemView.counter.text = dealsList1.get(position).table_time_limit.toString()
            itemView.dealRating.rating = dealsList1.get(position).rating.toFloat()
        }

        private fun loadImage(
            dealsList1: ArrayList<Deal>,
            position: Int
        ) {
            GlideApp.with(itemView.context)
                .load(dealsList1.get(position).cover_image)
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }

        private fun formateDate(date: String): String {
            var date1 = date
            var spf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val newDate: Date = spf.parse(date1)
            spf = SimpleDateFormat("hh:mm aaa")
            date1 = spf.format(newDate)
            return date1
        }

    }

}