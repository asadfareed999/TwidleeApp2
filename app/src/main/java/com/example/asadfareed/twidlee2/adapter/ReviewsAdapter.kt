package com.example.asadfareed.twidlee2.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Business_hours
import com.example.asadfareed.twidlee2.model.Deals
import com.example.asadfareed.twidlee2.model.Rating_summary
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.item_list_businesshours.view.*
import kotlinx.android.synthetic.main.item_list_restaurant_review_comments.view.*
import kotlinx.android.synthetic.main.item_list_restaurant_review_ratings.view.*
import kotlinx.android.synthetic.main.item_list_restaurant_review_summary.view.*
import java.text.SimpleDateFormat
import java.util.*


class ReviewsAdapter(ratingSummary: Rating_summary) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
      var ratings=ratingSummary

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==0) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_restaurant_review_summary, parent, false)
            return ViewHolder(v)
        }else if (viewType==1) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_restaurant_review_ratings, parent, false)
            return ViewHolder(v)
        }else {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_restaurant_review_comments, parent, false)
            return ViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(ratings,position)
    }

    override fun getItemViewType(position: Int): Int {
        /*var type:Int=0
        if (position==0){
            type=1
        }else if (position==1){
            type= 2
        }else{
            type= 3
        }*/
        return position
    }

    override fun getItemCount(): Int {
        val length:Int=ratings.ratings.size + 2
        return length
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        lateinit var ratings: Rating_summary

        fun bindItems(ratingSummary: Rating_summary, position: Int) {
            if (adapterPosition==0){
                setViewsDataSummary(ratingSummary)
            }else if (adapterPosition==1){
                setViewsDataProgress(ratingSummary)
            }else{
                setUserComment(ratingSummary)
            }
        }

        private fun setUserComment(ratingSummary: Rating_summary) {
            itemView.reviewUserName.text = ratingSummary.ratings.get(adapterPosition-2).user_name
            itemView.reviewUserComment.text = ratingSummary.ratings.get(adapterPosition-2).comments
            itemView.reviewUserRating.rating = ratingSummary.ratings.get(adapterPosition-2).overall.toFloat()
            utils.loadImage(
                itemView.context as FragmentActivity, itemView.reviewUserImage,
                R.drawable.ic_placeholder_avatar, ratingSummary.ratings.get(adapterPosition-2).user_picture
            )
        }

        private fun setViewsDataProgress(ratingSummary: Rating_summary) {
            itemView.progressBarOverall.progress = ratingSummary.overall.toInt() * 10
            itemView.textViewRatingOverall.text = ratingSummary.overall.toString()
            itemView.progressBarService.progress = ratingSummary.service.toInt() * 10
            itemView.textViewRatingService.text = ratingSummary.service.toString()
            itemView.progressBarFoodQuality.progress = ratingSummary.food_quality.toInt() * 10
            itemView.textViewRatingFoodQuality.text = ratingSummary.food_quality.toString()
            itemView.progressBarCleanliness.progress = ratingSummary.cleanliness.toInt() * 10
            itemView.textViewRatingCleanliness.text = ratingSummary.cleanliness.toString()
            itemView.progressBarPrice.progress = ratingSummary.price.toInt() * 10
            itemView.textViewRatingPrice.text = ratingSummary.price.toString()
        }

        private fun setViewsDataSummary(ratingSummary: Rating_summary) {
            var textData: String = ""
            itemView.ratingBarRestaurantReviewLatestRating.rating =
                ratingSummary.your_last_rating.toFloat()
            if (ratingSummary.your_last_rating.equals(0.0)) {
                itemView.textViewLastRateDate.text = "You have not rated yet"
            } else {
                textData = itemView.context.getString(R.string.hint_restaurant_rating_added_on) +
                        ratingSummary.last_ratting_date
                itemView.textViewLastRateDate.text = textData
            }
            textData = ratingSummary.total_ratings.toString().removeSuffix(".0") + " " +
                    itemView.context.getString(R.string.hint_restaurant_rating_customer_review)
            itemView.textViewCustomerReviewTotal.text = textData
            textData = ratingSummary.overall.toString() + " " +
                    itemView.context.getString(R.string.hint_restaurant_rating_out_of_5_stars)
            itemView.textViewCustomerRate.text = textData
            itemView.ratingBarRestaurantReviewCustomerRating.rating =
                ratingSummary.overall.toFloat()
        }


    }
}