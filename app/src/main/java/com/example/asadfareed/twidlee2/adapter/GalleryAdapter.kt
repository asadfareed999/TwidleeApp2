package com.example.asadfareed.twidlee2.adapter

import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import androidx.annotation.IntegerRes
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Image_galeries
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.dialog_gallery_image.view.*
import kotlinx.android.synthetic.main.item_list_restaurant_gallery.view.*


class GalleryAdapter(
    images: List<Image_galeries>,
    fragmentActivity: FragmentActivity
) : BaseAdapter() {
      var gallery=images
    var context=fragmentActivity

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val mInflater = (context).layoutInflater
        val  view:View= mInflater.inflate(R.layout.item_list_restaurant_gallery, parent, false)
        view.tag=position.toString()
        utils.loadImage(context,view.imageViewGallery,R.drawable.deal_placeholder,gallery.get(position).image_url)
        return view
    }

    override fun getItem(position: Int): Any {
        return gallery.get(position)
    }

    override fun getItemId(position: Int): Long {
       return gallery.get(position).id.toLong()
    }

    override fun getCount(): Int {
       return gallery.size
    }
}