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
import com.example.asadfareed.twidlee2.model.Restaurant
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.item_list_deal.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DealsAdapter(dealsList: ArrayList<DealRoom>) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {
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
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        private lateinit var deals:ArrayList<DealRoom>
        val imageView:ImageView=itemView.restaurantCoverImage

        fun bindItems(dealsList1: ArrayList<DealRoom>, position: Int) {
            deals=dealsList1
            loadImage(dealsList1, position)
            val cuisines = getCuisines(dealsList1, position)
            var (date, date2) = formatDates(dealsList1, position)
            setViewsData(dealsList1, position, cuisines, date, date2)
            itemView.setOnClickListener(this)
        }

        private fun getCuisines(
            dealsList1: ArrayList<DealRoom>,
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

        private fun formatDates(
            dealsList1: ArrayList<DealRoom>,
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
            dealsList1: ArrayList<DealRoom>,
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
            dealsList1: ArrayList<DealRoom>,
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

        override fun onClick(v: View?) {
            val viewModel = ViewModelProviders.of(itemView.context as FragmentActivity).get(RestaurantViewModel::class.java)
            viewModel.getRestaurantDetails(itemView.context as FragmentActivity, deals.get(adapterPosition).restaurant_id)
          //  viewModel.getRestaurantDetails(itemView.context as FragmentActivity, 9)

        }

        private fun loadFragment(fragment: Fragment, activity: FragmentActivity?) {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}