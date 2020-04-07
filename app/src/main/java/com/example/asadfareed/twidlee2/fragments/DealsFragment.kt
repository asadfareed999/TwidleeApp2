package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.DealsAdapter
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_deals.*
import kotlinx.android.synthetic.main.fragment_deals.view.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DealsFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DealsAdapter
    private lateinit var viewModel: ViewModel
    private lateinit var dealsList:MutableLiveData< ArrayList<Deal>>
    private lateinit var deals: ArrayList<DealRoom>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    private var index:Int=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view:View= inflater.inflate(R.layout.fragment_deals, container, false)
        initData(view)
        observeDeals()
        return  view
    }

    private fun observeDeals() {
        /*viewModel.getDeals(activity)
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
            activity!!.runOnUiThread {
                if (deals.size>0) {
                    if (swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.isRefreshing=false
                    }
                    adapter =
                        DealsAdapter(
                            deals
                        )
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }else{
                   observeDeals()
                }
            }

        }
    }

    private fun initData(view: View) {
       // activity!!.bottom_navigation_view.visibility=View.VISIBLE
        recyclerView = view.findViewById(R.id.fragmentDealsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        swipeRefreshLayout=view.swipeToRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
            swipeRefreshLayout.isRefreshing=true
        observeDeals()
    }
}