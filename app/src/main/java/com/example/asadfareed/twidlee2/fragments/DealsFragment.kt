package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.DealsAdapter
import com.example.asadfareed.twidlee2.adapter.ViewPagerAdapter
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.model.CompleteRestaurant
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.viewModel.RestaurantViewModel
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.view_tabs_deal.view.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class DealsFragment: Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DealsAdapter
    private lateinit var viewModel: ViewModel
    private lateinit var viewModelRestaurant: RestaurantViewModel
    private lateinit var dealsList:MutableLiveData< ArrayList<Deal>>
    private lateinit var deals: ArrayList<DealRoom>
    private lateinit var restaurants: ArrayList<CompleteRestaurant>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view:View= inflater.inflate(R.layout.view_tabs_deal, container, false)
        initData(view)
        //observeDeals()
        return  view
    }

      fun observeDeals() {
       /* viewModel.getDeals(activity)
            .observe(viewLifecycleOwner, Observer(function = fun(dealsList: ArrayList<Deal>?) {
                dealsList?.let {
                    adapter =
                        DealsAdapter(
                            dealsList
                        )
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))*/
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        executorService.execute {
            deals = viewModel.getRepoDeals(activity)
            viewModelRestaurant.getAllRestaurants(activity!!)
            activity!!.runOnUiThread {
                viewModelRestaurant.restaurantsAll
                    .observe(viewLifecycleOwner, Observer(function = fun(list: ArrayList<CompleteRestaurant>?) {
                        list?.let {
                            restaurants=list
                            if (deals.size>0 && restaurants.size>0) {
                                view!!.reloadViewDeals.visibility=View.GONE
                                loadTab(view!!)
                            }else{
                                view!!.reloadViewDeals.visibility=View.VISIBLE
                                view!!.reloadViewDeals.setOnClickListener {
                                    observeDeals()
                                }
                            }
                        }
                    }))

            }

        }
    }

    private fun initData(view: View) {
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModelRestaurant = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)
        observeDeals()
    }

    private fun loadTab(view: View) {
        val tabs = view.Tabs
        val viewPager: ViewPager = view.view_pagerDealsRestaurant
        viewPager.adapter = ViewPagerAdapter(deals,restaurants,this)
        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.setupWithViewPager(viewPager)
        setupTabIcons(tabs)
    }

    private fun setupTabIcons(tabs: TabLayout) {
        tabs.getTabAt(0)!!.text = "Deals"
        tabs.getTabAt(1)!!.text= "Restaurants"

    }

}