package com.example.asadfareed.twidlee2.adapter

import android.location.Location
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
import com.example.asadfareed.twidlee2.fragments.restaurant.RestaurantFragment
import com.example.asadfareed.twidlee2.model.FavoritesParameter
import com.example.asadfareed.twidlee2.utils.counter
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.FavoritesViewModel
import kotlinx.android.synthetic.main.item_list_deal.view.*
import kotlinx.android.synthetic.main.item_list_feature_deal_recycler.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DealsAdapter(
    featureDeals: ArrayList<DealRoom>,
    Deals: ArrayList<DealRoom>,
    contextFragment: DealsFragment,
    location: Location?
) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {

    var dealList2=Deals
    val contextFragment=contextFragment
    var featureDeals=featureDeals
    private val currentLocation:Location?=location

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
            holder.bindItems(featureDeals, position, contextFragment, currentLocation)
        }else{
            holder.bindItems(dealList2, position-1, contextFragment,currentLocation)
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
            contextFragment: DealsFragment,
            currentLocation: Location?
        ) {
            if (adapterPosition==0){
                setUpFeatureDeals(dealsList1, contextFragment,currentLocation)

            }else {
                setupDeals(dealsList1, position, contextFragment,currentLocation)
            }
        }

        private fun setupDeals(
            dealsList1: ArrayList<DealRoom>,
            position: Int,
            contextFragment: DealsFragment,
            currentLocation: Location?
        ) {
            imageView = itemView.restaurantCoverImage
            deals = dealsList1
            utils.loadImage(itemView.context as FragmentActivity,imageView,
                R.drawable.deal_placeholder,dealsList1.get(position).cover_image)
            val cuisines = getCuisines(dealsList1.get(position))
            var (date, date2) = formatDates(dealsList1.get(position))
            setViewsData(dealsList1.get(position), cuisines, date, date2, contextFragment,currentLocation)
            itemView.setOnClickListener(this)
        }

        private fun setUpFeatureDeals(
            deal: ArrayList<DealRoom>,
            contextFragment: DealsFragment,
            currentLocation: Location?
        ) {
            recyclerView = itemView.nestedRecyclerViewFeatureRecycler
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            adapter = DealNestedAdapter(deal, contextFragment,currentLocation)
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
            contextFragment: DealsFragment,
            currentLocation: Location?
        ) {
            itemView.restaurantName.text = deal.restaurant_name
            if (currentLocation!=null){
                val location=Location("")
                location.latitude=deal.address.latitude
                location.longitude=deal.address.longitude
                val array:FloatArray = FloatArray(1)
                Location.distanceBetween(currentLocation.latitude,currentLocation.longitude,
                    location.latitude,location.longitude,array)
                val distanceInKM:Float=array.get(0)/1000
                val decim = DecimalFormat("0.00")
                val price2: Double = decim.format(distanceInKM).toDouble()
                val text=deal.address.display_address+" | "+price2.toString()+" KM"
                itemView.restaurantAddress.text = text
            }else {
                itemView.restaurantAddress.text = deal.address.display_address
            }
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
                val favorite = itemView.markFavorite.isSelected
                val favoritesParameter =
                    FavoritesParameter(deal.restaurant_id, !favorite)
                viewModel.makeFavoriteRestaurants(
                    itemView.context as FragmentActivity, favoritesParameter,
                    itemView.markFavorite
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
            utils.loadFragment2(RestaurantFragment(deals.get(adapterPosition-1).restaurant_id), itemView.context as FragmentActivity)
        }
    }
}