package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import com.example.asadfareed.twidlee2.fragments.restaurant.RestaurantFragment
import com.example.asadfareed.twidlee2.glidemodule.GlideApp
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.item_list_feature_deal.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DealNestedAdapter(dealsList1: ArrayList<DealRoom>,
                        dealsFragment: DealsFragment)
    : RecyclerView.Adapter<DealNestedAdapter.ViewHolder>() {

    var featureDeals=dealsList1
    val contextFragment=dealsFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_feature_deal, parent, false)
            return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(featureDeals.get(position),position,contextFragment)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return featureDeals.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        val imageView: ImageView =itemView.restaurantCoverImageFeatureDeal
        private lateinit var deal:DealRoom

        fun bindItems(featureDeal: DealRoom,position: Int,
                      contextFragment: DealsFragment) {
            deal=featureDeal
            utils.loadImage(itemView.context as FragmentActivity,imageView,R.drawable.deal_placeholder,featureDeal.cover_image)
            val cuisines = getCuisines(featureDeal)
            var (date, date2) = formatDates(featureDeal, position)
            setViewsData(featureDeal, cuisines, date, date2, contextFragment)
            itemView.setOnClickListener(this)
        }

        private fun formatDates(
            deal: DealRoom,
            position: Int
        ): Pair<String, String> {
            var date = deal.start_time
            var date2 = deal.end_time
            // var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
            date = formateDate(date)
            date2 = formateDate(date2)
            return Pair(date, date2)
        }

        private fun setViewsData(
            deal: DealRoom,
            cuisines: StringBuilder,
            date: String,
            date2: String,
            contextFragment: DealsFragment
        ) {
            itemView.restaurantNameFeatureDeal.text = deal.restaurant_name
            itemView.restaurantAddressFeatureDeal.text = deal.address.display_address
            itemView.restaurantCuisinesFeatureDeal.text = cuisines
            itemView.dealOfferFeatureDeal.text = deal.title
            itemView.dealTimeFeatureDeal.text = date + "-" + date2
            // itemView.counter.text = dealsList1.get(position).table_time_limit.toString()
            itemView.dealRatingFeatureDeal.rating = deal.rating.toFloat()
        }

        private fun getCuisines(
            deal: DealRoom)
                : StringBuilder {
            val sb = StringBuilder()
            val list:List<String> = deal.cuisines
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

        private fun formateDate(date: String): String {
            var date1 = date
            var spf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val newDate: Date = spf.parse(date1)!!
            spf = SimpleDateFormat("hh:mm aaa")
            date1 = spf.format(newDate)
            return date1
        }

        override fun onClick(v: View?) {
            utils.loadFragment2(RestaurantFragment(deal.restaurant_id), itemView.context as FragmentActivity)
        }
    }
}