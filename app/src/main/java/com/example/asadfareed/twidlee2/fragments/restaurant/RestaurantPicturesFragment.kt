package com.example.asadfareed.twidlee2.fragments.restaurant

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.GalleryAdapter
import com.example.asadfareed.twidlee2.model.Image_galeries
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.dialog_gallery_image.view.*
import kotlinx.android.synthetic.main.fragment_restaurant_gallery.view.*


class RestaurantPicturesFragment(imageGaleries: List<Image_galeries>) : Fragment() {

    private var images=imageGaleries
    private lateinit var gridView: GridView
    private lateinit var adapter: GalleryAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_restaurant_gallery, container, false)
        gridView=view.gridViewRestaurantGallery
        adapter = GalleryAdapter(images,activity as FragmentActivity)
        gridView.numColumns=3
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()
        gridviewImagePreview()
        return view
    }

    private fun gridviewImagePreview() {
        gridView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val settingsDialog = Dialog(activity!!)
                settingsDialog.getWindow()!!.requestFeature(Window.FEATURE_NO_TITLE)
                settingsDialog.getWindow()!!.setBackgroundDrawable(
                     ColorDrawable(Color.TRANSPARENT)
                )
                val image: View = activity!!.layoutInflater.inflate(
                    R.layout.dialog_gallery_image
                    , null
                )
                utils.loadImage(
                    activity!!,
                    image.galleryImageView,
                    R.drawable.deal_placeholder,
                    images.get(position).image_url
                )
                settingsDialog.setContentView(image)
                settingsDialog.show()
            }
    }
}