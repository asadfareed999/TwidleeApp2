package com.example.asadfareed.twidlee2.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.FavoritesAdapter
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.viewModel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_deals.view.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import kotlinx.android.synthetic.main.fragment_termservices_privacy.view.toolbar
import kotlinx.android.synthetic.main.toolbar_simple.view.*

class FavoritesFragment() : Fragment() , SwipeRefreshLayout.OnRefreshListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoriteList: MutableLiveData<ArrayList<Deal>>
    private lateinit var view1: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)
        initData(view)
        setViewsData(view)
        observeFavorites(view)
        view.reloadView2.setOnClickListener {
            val viewModel=ViewModelProviders.of(activity!!)
                .get(FavoritesViewModel::class.java)
            viewModel.getFavoritesRestaurant(activity,view)
        }
        return view
    }

    private fun setViewsData(view: View) {
        val view2: View = view.toolbar
        view2.toolbar_title.text = activity!!.getString(R.string.title_favorites)
        view2.toolbar_title.visibility = View.VISIBLE
        view.reloadView2.visibility=View.VISIBLE
        view.reloadView2.text=view.context.getString(R.string.message_no_favorites)
    }
    private fun initData(view: View) {
        recyclerView = view.fragmentFavoritesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        swipeRefreshLayout=view.swipeToRefreshFav
        swipeRefreshLayout.setOnRefreshListener(this)
        view1=view
    }

    private fun observeFavorites(view: View) {
        viewModel.getFavoritesRestaurant(activity!!,view)
            .observe(viewLifecycleOwner, Observer(function = fun(dealsList: ArrayList<Deal>?) {
                dealsList?.let {
                    val size=dealsList.size
                        if (swipeRefreshLayout.isRefreshing) {
                            swipeRefreshLayout.isRefreshing = false
                        }
                    adapter =
                        FavoritesAdapter(
                            dealsList
                        )
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    if (adapter.itemCount==0) {
                        view.reloadView2.visibility=View.VISIBLE
                        view.reloadView2.text=view.context.getString(R.string.message_no_favorites)
                    }else{
                        view.reloadView2.visibility=View.GONE
                    }
                }
            }))
    }
    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing=true
        observeFavorites(view!!)
    }
}