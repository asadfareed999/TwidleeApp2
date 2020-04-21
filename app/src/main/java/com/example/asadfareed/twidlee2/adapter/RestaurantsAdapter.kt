package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import com.example.asadfareed.twidlee2.model.CompleteRestaurant
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.item_list_restaurant.view.*
import java.util.*


class RestaurantsAdapter(
    restaurantsList: ArrayList<CompleteRestaurant>,
    dealsFragment: DealsFragment
) : RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    var restaurantsList1= restaurantsList
    val contextFragment=dealsFragment

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_restaurant, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(restaurantsList1.get(position),contextFragment)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return restaurantsList1.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        private lateinit var restaurants:CompleteRestaurant
        val imageView:ImageView=itemView.restaurantListImage

        fun bindItems(
            restaurant: CompleteRestaurant,
            contextFragment: DealsFragment
        ) {
            restaurants=restaurant
            utils.loadImage(itemView.context as FragmentActivity,imageView,
                R.drawable.deal_placeholder,restaurant.cover_image)
            val cuisines = getCuisines(restaurant)
            setViewsData(restaurant, cuisines,contextFragment)
            itemView.setOnClickListener(this)
        }

        private fun getCuisines(
            restaurant: CompleteRestaurant): StringBuilder {
            val sb = StringBuilder()
            val list:List<String> = restaurant.cuisine_types
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


        private fun setViewsData(
            restaurant: CompleteRestaurant,
            cuisines: StringBuilder,
            contextFragment: DealsFragment
        ) {
            itemView.restaurantListName.text = restaurant.name
            itemView.restaurantListCuisines.text = cuisines
           // itemView.dealOfferrestaurantList.text = restaurantsList1.get(position).title
            itemView.dealRatingrestaurantList.rating = restaurant.rating.toFloat()
            if (restaurant.is_favorite) {
                itemView.markFavoriterestaurantList.isSelected=true
            }
           /* itemView.markFavoriterestaurantList.setOnClickListener {
                val viewModel=ViewModelProviders.of(itemView.context as FragmentActivity)
                    .get(FavoritesViewModel::class.java)
                val favorite=dealsList1.get(position).is_favorite
                val favoritesParameter=FavoritesParameter(dealsList1.get(position).restaurant_id,!favorite)
               viewModel.makeFavorite(itemView.context as FragmentActivity,favoritesParameter,
                   itemView.markFavorite,contextFragment)
            }*/
        }

        override fun onClick(v: View?) {
            val viewModel = ViewModelProviders.of(itemView.context as FragmentActivity).get(RestaurantViewModel::class.java)
            viewModel.getRestaurantDetails(itemView.context as FragmentActivity, restaurants.id)
        }

    }

}