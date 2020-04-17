package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.DealsAdapter
import com.example.asadfareed.twidlee2.adapter.ViewPagerAdapter
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.view_tabs_deal.view.*


class DealsFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DealsAdapter
    private lateinit var viewModel: ViewModel
    private lateinit var dealsList:MutableLiveData< ArrayList<Deal>>
    private lateinit var deals: ArrayList<DealRoom>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view:View= inflater.inflate(R.layout.view_tabs_deal, container, false)
        initData(view)
        //observeDeals()
        return  view
    }

     /* fun observeDeals() {
        *//*viewModel.getDeals(activity)
            .observe(viewLifecycleOwner, Observer(function = fun(dealsList: ArrayList<Deal>?) {
                dealsList?.let {
                    adapter =
                        DealsAdapter(
                            dealsList
                        )
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))*//*
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        executorService.execute {
            deals = viewModel.getRepoDeals(activity)
            activity!!.runOnUiThread {
                if (deals.size>0) {
                    view!!.reloadViewDeals.visibility=View.GONE
                    if (swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.isRefreshing=false
                    }
                    adapter =
                        DealsAdapter(
                            deals,this
                        )
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }else{
                  // observeDeals()
                    view!!.reloadViewDeals.visibility=View.VISIBLE
                    view!!.reloadViewDeals.setOnClickListener {
                        observeDeals()
                    }
                }
            }

        }
    }*/

    private fun initData(view: View) {
        /*recyclerView = view.findViewById(R.id.fragmentDealsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        swipeRefreshLayout=view.swipeToRefresh
        swipeRefreshLayout.setOnRefreshListener(this)*/
        val list: MutableList<String> = ArrayList()
        list.add("Deals")
        list.add("Restaurants")
        val viewPager: ViewPager = view.view_pagerDealsRestaurant
        viewPager.adapter = ViewPagerAdapter(list)
    }

    override fun onRefresh() {
            swipeRefreshLayout.isRefreshing=true
       // observeDeals()
    }



}