package com.example.asadfareed.twidlee2.adapter

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import com.example.asadfareed.twidlee2.model.CompleteRestaurant
import kotlinx.android.synthetic.main.deals_view.view.*


class ViewPagerAdapter(
    list: ArrayList<DealRoom>,
    list2: ArrayList<CompleteRestaurant>,
    dealsFragment: DealsFragment,
    locationCurrent: Location?
) :PagerAdapter(),SwipeRefreshLayout.OnRefreshListener {

    val dealList=list
    val restaurantList=list2
    val context=dealsFragment
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DealsAdapter
    private lateinit var adapter2: RestaurantsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var dealList2:ArrayList<DealRoom>
    lateinit var featureDeals:ArrayList<DealRoom>
    var location: Location?=locationCurrent

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.deals_view, container, false) as ViewGroup
        initData(view)
        swipeRefreshHandler(view)
        if (position==0){
            loadDeals()
        }else{
            loadRestaurants()
        }
        container.addView(view)
        return view
    }

    private fun loadRestaurants() {
        adapter2 =
            RestaurantsAdapter(
                restaurantList, context
            )
        recyclerView.adapter = adapter2
        adapter2.notifyDataSetChanged()
    }

    private fun loadDeals() {
        getLists(dealList)
        adapter =
            DealsAdapter(
                featureDeals, dealList2, context,location
            )
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initData(view: ViewGroup) {
        recyclerView = view.findViewById(R.id.fragmentDealsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
    }

    private fun swipeRefreshHandler(view: ViewGroup) {
        swipeRefreshLayout = view.swipeToRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getLists(dealList: ArrayList<DealRoom>) {
        val size=dealList.size
        featureDeals = ArrayList()
        dealList2= ArrayList()
        for (i in 0 until size){
            if (dealList.get(i).restaurant_is_featured==true){
                featureDeals.add(dealList.get(i))
            }else{
                dealList2.add(dealList.get(i))
            }
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
          return  view==`object`
    }

    override fun getCount(): Int {
          return  2
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun onRefresh() {
        context.observeDeals()
    }

}