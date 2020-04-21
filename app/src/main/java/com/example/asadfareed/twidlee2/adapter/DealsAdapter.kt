package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import com.example.asadfareed.twidlee2.model.FavoritesParameter
import com.example.asadfareed.twidlee2.utils.counter
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.FavoritesViewModel
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.item_list_deal.view.*
import kotlinx.android.synthetic.main.item_list_feature_deal_recycler.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DealsAdapter(
    featureDeals:ArrayList<DealRoom>,
    Deals:ArrayList<DealRoom>,
    contextFragment: DealsFragment
    ) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {

    var dealList2=Deals
    val contextFragment=contextFragment
    var featureDeals=featureDeals

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==0){
            val v =
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_feature_deal_recycler
                    , parent, false)
            return ViewHolder(v)
        }else {
            val v =
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_deal, parent, false)
            return ViewHolder(v)
        }
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==0) {
            holder.bindItems(featureDeals, position, contextFragment)
        }else{
            holder.bindItems(dealList2, position-1, contextFragment)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position==0){
            return 0
        }else{
            return 1
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dealList2.size+1
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        private lateinit var deals:ArrayList<DealRoom>
        private lateinit var imageView:ImageView
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: DealNestedAdapter

        fun bindItems(
            dealsList1: ArrayList<DealRoom>,
            position: Int,
            contextFragment: DealsFragment
        ) {
            if (adapterPosition==0){
                setUpFeatureDeals(dealsList1, contextFragment)

            }else {
                setupDeals(dealsList1, position, contextFragment)
            }
        }

        private fun setupDeals(
            dealsList1: ArrayList<DealRoom>,
            position: Int,
            contextFragment: DealsFragment
        ) {
            imageView = itemView.restaurantCoverImage
            deals = dealsList1
            utils.loadImage(itemView.context as FragmentActivity,imageView,
                R.drawable.deal_placeholder,dealsList1.get(position).cover_image)
            val cuisines = getCuisines(dealsList1.get(position))
            var (date, date2) = formatDates(dealsList1.get(position))
            setViewsData(dealsList1.get(position), cuisines, date, date2, contextFragment)
            itemView.setOnClickListener(this)
        }

        private fun setUpFeatureDeals(
            deal: ArrayList<DealRoom>,
            contextFragment: DealsFragment
        ) {
            recyclerView = itemView.nestedRecyclerViewFeatureRecycler
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            adapter = DealNestedAdapter(deal, contextFragment)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        private fun getCuisines(
            deal: DealRoom
        ): StringBuilder {
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

        private fun formatDates(
            deal: DealRoom)
                : Pair<String, String> {
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
            itemView.restaurantName.text = deal.restaurant_name
            itemView.restaurantAddress.text = deal.address.display_address
            itemView.restaurantCuisines.text = cuisines
            itemView.dealOffer.text = deal.title
            itemView.dealTime.text = date + "-" + date2
           // itemView.counter.text = dealsList1.get(position).table_time_limit.toString()
            itemView.dealRating.rating = deal.rating.toFloat()
            counter.startTimer(itemView.counterDeals,deal.end_time,deal.start_time)
            if (deal.is_favorite) {
                itemView.markFavorite.isSelected=true
            }
            favoriteClick(deal, contextFragment)
        }

        private fun favoriteClick(
            deal: DealRoom,
            contextFragment: DealsFragment
        ) {
            itemView.markFavorite.setOnClickListener {
                val viewModel = ViewModelProviders.of(itemView.context as FragmentActivity)
                    .get(FavoritesViewModel::class.java)
                val favorite = deal.is_favorite
                val favoritesParameter =
                    FavoritesParameter(deal.restaurant_id, !favorite)
                viewModel.makeFavorite(
                    itemView.context as FragmentActivity, favoritesParameter,
                    itemView.markFavorite, contextFragment
                )
            }
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
            val viewModel = ViewModelProviders.of(itemView.context as FragmentActivity).get(RestaurantViewModel::class.java)
            viewModel.getRestaurantDetails(itemView.context as FragmentActivity, deals.get(adapterPosition-1).restaurant_id)
        }
    }
}