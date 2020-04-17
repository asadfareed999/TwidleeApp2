package com.example.asadfareed.twidlee2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.asadfareed.twidlee2.R


class ViewPagerAdapter(list: MutableList<String>) :PagerAdapter() {

    val listViews=list

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_list_restaurant, container, false) as ViewGroup
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
          return  view==`object`
    }

    override fun getCount(): Int {
          return  listViews.size
    }


}