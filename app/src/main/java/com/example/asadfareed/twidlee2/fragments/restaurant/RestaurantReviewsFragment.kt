package com.example.asadfareed.twidlee2.fragments.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.ReviewsAdapter
import com.example.asadfareed.twidlee2.model.Rating_summary
import kotlinx.android.synthetic.main.fragment_restaurant_reviews.view.*

class RestaurantReviewsFragment(restaurantDetails: Rating_summary) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReviewsAdapter
    private val ratingSummary=restaurantDetails

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant_reviews, container, false)
        setUpRecyclerview(view)
        return view
    }

    private fun setUpRecyclerview(view: View) {
        recyclerView = view.recyclerViewRestaurantReviews
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = ReviewsAdapter(ratingSummary)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}