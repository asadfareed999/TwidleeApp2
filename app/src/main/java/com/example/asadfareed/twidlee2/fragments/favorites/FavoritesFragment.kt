package com.example.asadfareed.twidlee2.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.FavoritesAdapter
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.viewModel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import kotlinx.android.synthetic.main.fragment_termservices_privacy.view.*
import kotlinx.android.synthetic.main.fragment_termservices_privacy.view.toolbar
import kotlinx.android.synthetic.main.toolbar_simple.view.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoritesFragment() : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoriteList: MutableLiveData<ArrayList<Deal>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)
        initData(view)
        setViewsData(view)
        observeDeals()
        return view
    }

    private fun setViewsData(view: View) {
        val view2: View = view.toolbar
        view2.toolbar_title.text = activity!!.getString(R.string.title_favorites)
        view2.toolbar_title.visibility = View.VISIBLE
    }
    private fun initData(view: View) {
        recyclerView = view.fragmentFavoritesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
    }

    private fun observeDeals() {
        viewModel.getFavoritesRestaurant(activity!!)
            .observe(viewLifecycleOwner, Observer(function = fun(dealsList: ArrayList<Deal>?) {
                dealsList?.let {
                    val size=dealsList.size
                    adapter =
                        FavoritesAdapter(
                            dealsList
                        )
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }))
    }
}