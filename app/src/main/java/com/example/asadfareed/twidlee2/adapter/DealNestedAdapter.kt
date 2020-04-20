package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import com.example.asadfareed.twidlee2.glidemodule.GlideApp
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

        fun bindItems(featureDeal: DealRoom,position: Int,
                      contextFragment: DealsFragment) {
            loadImage(featureDeal, position)
            val cuisines = getCuisines(featureDeal, position)
            var (date, date2) = formatDates(featureDeal, position)
            setViewsData(featureDeal, position, cuisines, date, date2, contextFragment)
            itemView.setOnClickListener(this)
        }

        private fun formatDates(
            dealsList1: DealRoom,
            position: Int
        ): Pair<String, String> {
            var date = dealsList1.start_time
            var date2 = dealsList1.end_time
            // var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
            date = formateDate(date)
            date2 = formateDate(date2)
            return Pair(date, date2)
        }

        private fun setViewsData(
            dealsList1: DealRoom,
            position: Int,
            cuisines: StringBuilder,
            date: String,
            date2: String,
            contextFragment: DealsFragment
        ) {
            itemView.restaurantNameFeatureDeal.text = dealsList1.restaurant_name
            itemView.restaurantAddressFeatureDeal.text = dealsList1.address.display_address
            itemView.restaurantCuisinesFeatureDeal.text = cuisines
            itemView.dealOfferFeatureDeal.text = dealsList1.title
            itemView.dealTimeFeatureDeal.text = date + "-" + date2
            // itemView.counter.text = dealsList1.get(position).table_time_limit.toString()
            itemView.dealRatingFeatureDeal.rating = dealsList1.rating.toFloat()
            /*counter.startTimer(itemView.counterDeals,dealsList1.get(position).end_time,dealsList1.get(position).start_time)
            if (dealsList1.get(position).is_favorite) {
                itemView.markFavorite.isSelected=true
            }
            itemView.markFavorite.setOnClickListener {
                val viewModel=ViewModelProviders.of(itemView.context as FragmentActivity)
                    .get(FavoritesViewModel::class.java)
                val favorite=dealsList1.get(position).is_favorite
                val favoritesParameter=FavoritesParameter(dealsList1.get(position).restaurant_id,!favorite)
                viewModel.makeFavorite(itemView.context as FragmentActivity,favoritesParameter,
                    itemView.markFavorite,contextFragment)
            }*/
        }

        private fun getCuisines(
            dealsList1: DealRoom,
            position: Int
        ): StringBuilder {
            val sb = StringBuilder()
            val list:List<String> = dealsList1.cuisines
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

        private fun loadImage(
            dealsList1: DealRoom,
            position: Int
        ) {
            GlideApp.with(itemView.context)
                .load(dealsList1.cover_image)
                .fitCenter()
                .placeholder(R.drawable.deal_placeholder)
                .into(imageView)
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
            /*val viewModel = ViewModelProviders.of(itemView.context as FragmentActivity).get(
                RestaurantViewModel::class.java)
            viewModel.getRestaurantDetails(itemView.context as FragmentActivity, deals.get(adapterPosition).restaurant_id)*/
            //  viewModel.getRestaurantDetails(itemView.context as FragmentActivity, 9)

        }

    }
}