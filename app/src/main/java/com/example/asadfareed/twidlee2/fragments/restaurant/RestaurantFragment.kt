package com.example.asadfareed.twidlee2.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.TabAdapter
import com.example.asadfareed.twidlee2.model.FavoritesParameter
import com.example.asadfareed.twidlee2.model.Restaurant
import com.example.asadfareed.twidlee2.utils.utils
import com.example.asadfareed.twidlee2.viewModel.FavoritesViewModel
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_restaurant.view.*

class RestaurantFragment(id: Int) : Fragment() {

    private lateinit var viewModel: ViewModel
    lateinit var restaurantDetails:Restaurant
    private val idRestaurant = id

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant, container, false)
        fetchData(view)
        return view
    }

    private fun fetchData(view: View) {
        val viewModel = ViewModelProviders.of(this).get(
            RestaurantViewModel::class.java)
        viewModel.getRestaurantDetails(view.context as FragmentActivity,idRestaurant )
        viewModel.restaurantDetails.observeForever(Observer (function = fun(restaurant: Restaurant?) {
            restaurant?.let {
                restaurantDetails=restaurant
                setupData(view)
                setTabs(view)
            }
        }))
    }

    private fun setTabs(view: View) {
        val viewPager = view.view_pager
        val tabs = view.restaurantTabs
        val adapter =
            TabAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(RestaurantDealsFragment(restaurantDetails.deals), getString(R.string.deals))
        adapter.addFragment(RestaurantContactFragment(restaurantDetails), getString(R.string.contact))
        adapter.addFragment(RestaurantReviewsFragment(restaurantDetails.rating_summary), getString(R.string.reviews))
        adapter.addFragment(RestaurantPicturesFragment(restaurantDetails.image_galeries), getString(R.string.pictures))
        adapter.addFragment(RestaurantMenuFragment(restaurantDetails.menu), getString(R.string.menu))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        setupTabIcons(tabs)
    }

    private fun setupTabIcons(tabs: TabLayout) {
        tabs.getTabAt(0)!!.icon = activity!!.getDrawable(R.drawable.ic_cupcake)
        tabs.getTabAt(1)!!.icon = activity!!.getDrawable(R.drawable.ic_contact)
        tabs.getTabAt(2)!!.icon = activity!!.getDrawable(R.drawable.ic_review)
        tabs.getTabAt(3)!!.icon = activity!!.getDrawable(R.drawable.ic_gallery)
        tabs.getTabAt(4)!!.icon = activity!!.getDrawable(R.drawable.ic_menu)
    }

    private fun setupData(view: View) {
        utils.loadImage(
            activity!!,
            view.restaurantHeaderThumbnail,
            R.drawable.restaurant_header_placeholder,
            restaurantDetails.cover_image
        )
        view.restaurantRating.rating = restaurantDetails.rating.toFloat()
        view.restaurantName.text = restaurantDetails.name
        view.markFavoriteRestaurantHome.isSelected = restaurantDetails.is_favorite
        view.restaurantCuisine.text =
            getListItems(restaurantDetails.cuisines) + "\n" + getListItems(restaurantDetails.food_types)
        view.restaurantAddress.text = restaurantDetails.address.display_address
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
       favoriteClick(view)
    }

    private fun favoriteClick(view: View) {
        view.markFavoriteRestaurantHome.setOnClickListener {
            val viewModel = ViewModelProviders.of(this)
                .get(FavoritesViewModel::class.java)
            val favorite = view.markFavoriteRestaurantHome.isSelected
            val favoritesParameter =
                FavoritesParameter(restaurantDetails.id, !favorite)
            viewModel.makeFavoriteRestaurants(
                view.context as FragmentActivity, favoritesParameter,
                view.markFavoriteRestaurantHome)
        }
    }

    private fun getListItems(cuisines: List<String>): String {
        val sb = StringBuilder()
        for (i in 0 until cuisines.size) {
            if (i<cuisines.size-1){
                sb.append(cuisines[i]+ ",")
            }else {
                sb.append(cuisines[i])
            }
        }
        val cuisines1:String = sb.toString()
        return cuisines1
    }

}