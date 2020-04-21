package com.example.asadfareed.twidlee2.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Business_hours
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.item_list_businesshours.view.*
import java.text.SimpleDateFormat
import java.util.*


class BusinessHoursAdapter(businessHours: List<Business_hours>) : RecyclerView.Adapter<BusinessHoursAdapter.ViewHolder>() {
      var hours=businessHours

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_businesshours, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(hours.get(position))
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(hours: Business_hours) {
            var day=utils.spannableStringColor(hours.day)
            var (date, date2) = formatDates(hours)
            val textHours=TextUtils.concat(day,"\n"+date+"  -  "+date2+"\n")
            itemView.businessDayHour.text=textHours
        }
        private fun formatDates(
            hours: Business_hours
        )
                : Pair<String, String> {
            var date = hours.slots.get(0).time_start
            var date2 = hours.slots.get(0).time_end
            // var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
            date = formateTime(date)
            date2 = formateTime(date2)
            return Pair(date, date2)
        }

        fun formateTime(date: String): String {
            var date1 = date
            var spf = SimpleDateFormat("hh:mm:ss")
            val newDate: Date = spf.parse(date1)
            spf = SimpleDateFormat("hh:mm a")
            date1 = spf.format(newDate)
            return date1
        }

    }
}