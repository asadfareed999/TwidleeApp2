package com.example.asadfareed.twidlee2.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.Deals
import com.example.asadfareed.twidlee2.utils.utils
import kotlinx.android.synthetic.main.item_list_restaurant_deal.view.*
import kotlinx.android.synthetic.main.item_list_restaurant_reserved_deal.view.dealDetails
import kotlinx.android.synthetic.main.item_list_restaurant_reserved_deal.view.dealName
import kotlinx.android.synthetic.main.view_timer.view.*
import java.text.SimpleDateFormat
import java.util.*


class RestaurantDealsAdapter(dealsRestaurant: List<Deals>) : RecyclerView.Adapter<RestaurantDealsAdapter.ViewHolder>() {
      var deals= dealsRestaurant

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_restaurant_deal, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(deals,position)
        holder.radioButtonTakeAway.setOnClickListener {
            if (holder.radioButtonTakeAway.isChecked) {
                holder.spinnerGuests.isEnabled = false
                holder.spinnerArrival.isEnabled=true
                val items = arrayOf(
                    holder.context.getString(R.string.take_away_time),
                    holder.deals.get(position).takeaway_slots.get(0).slot
                )
               holder.setSpinner(items, holder.spinnerArrival)
            }
        }

        holder.radioButtonDineIn.setOnClickListener {
            if (holder.radioButtonDineIn.isChecked) {
                holder.spinnerGuests.isEnabled = true
                val items = arrayOf(
                    holder.context.getString(R.string.arrival_time),
                    holder.deals.get(position).dine_in_slots.get(0).slot
                )
                holder.setSpinner(items, holder.spinnerArrival)
            }
        }
    }

    override fun getItemCount(): Int {
        return deals.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        lateinit var deals: List<Deals>
        lateinit var radioButtonTakeAway: RadioButton
        lateinit var radioButtonDineIn: RadioButton
        lateinit var spinnerArrival: Spinner
        lateinit var spinnerGuests: Spinner
        lateinit var context: Context

        fun bindItems(dealsList: List<Deals>, position: Int) {
            initViews(dealsList)
            val text = fetchData(dealsList, position)
            var (date, date2) = formatDates(dealsList, position)
            setViewsData(dealsList, position, date, date2, text)
        }

        private fun fetchData(
            dealsList: List<Deals>,
            position: Int
        ): CharSequence? {
            var ss1 = spannableString(itemView.context.getString(R.string.meal_type))
            val mealType =
                TextUtils.concat(ss1, " " + getListData(dealsList.get(position).meal_types))
            ss1 = spannableString(itemView.context.getString(R.string.exclusions))
            val Exclusions =
                TextUtils.concat(ss1, " " + getListData(dealsList.get(position).exclusions))
            val text = TextUtils.concat(mealType, "\n")
            val text2 = TextUtils.concat(text,Exclusions)
           var text3=TextUtils.concat(utils.spannableStringBold(dealsList.get(position).description),"\n")
             text3 = TextUtils.concat(text3,text2)
            return text3
        }

        private fun setViewsData(
            dealsList: List<Deals>,
            position: Int,
            date: String,
            date2: String,
            text: CharSequence?
        ) {
            itemView.dealName.text = dealsList.get(position).title
            itemView.dealTimeDuration.text = date + " - " + date2
            itemView.dealDetails.text = text
            itemView.counter.text=itemView.context.getString(R.string.deal_expired)
            if (dealsList.get(position).dine_in_slots.isEmpty()){
                spinnerGuests.visibility=View.GONE
                spinnerArrival.visibility=View.GONE
                radioButtonDineIn.visibility=View.GONE
                radioButtonTakeAway.visibility=View.GONE
                itemView.reserveButton.visibility=View.GONE
            }else {
                setSpinner(
                    itemView.context.resources.getStringArray(R.array.guests),
                    itemView.numberOfGuests
                )
                val items =
                    arrayOf(
                        itemView.context.getString(R.string.arrival_time),
                        dealsList.get(position).dine_in_slots.get(0).slot
                    )
                setSpinner(items, itemView.arrivalTime)
            }
        }

        private fun initViews(dealsList: List<Deals>) {
            deals = dealsList
            radioButtonTakeAway = itemView.redeemType.redeemTypeTakeaway
            radioButtonDineIn = itemView.redeemType.redeemTypeDineIn
            spinnerGuests = itemView.numberOfGuests
            spinnerArrival = itemView.arrivalTime
            context = itemView.context
        }

        fun setSpinner(intArray: Array<String>, numberOfGuests: Spinner) {
            val spinner = numberOfGuests
            val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                itemView.context, R.layout.list_item_simple_spinner, intArray)
            //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerArrayAdapter
        }

        private fun spannableString(string: String): SpannableString {
            val ss1 = SpannableString(string)
            ss1.setSpan(RelativeSizeSpan(1.1f), 0, ss1.length, 0) // set size
            ss1.setSpan(StyleSpan(Typeface.BOLD), 0, ss1.length, 0) // set style
            return ss1
        }

        private fun getListData(listdata: List<String>): StringBuilder {
            val sb = StringBuilder()
            val list:List<String> = listdata
            for (i in 0 until list.size) {
                if (i<list.size-1){
                    sb.append(list[i]+ ",")
                }else {
                    sb.append(list[i])
                }
            }
            val data = sb
            return data
        }

        private fun formatDates(
            dealsList1: List<Deals>,
            position: Int
        ): Pair<String, String> {
            var date = dealsList1.get(position).start_time
            var date2 = dealsList1.get(position).end_time
            // var spf = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa")
            date = formateDate(date)
            date2 = formateDate(date2)
            val date3=formateDate2(dealsList1.get(position).start_time)
            date=date3+" "+date
            return Pair(date, date2)
        }

        private fun formateDate(date: String): String {
            var date1 = date
            var spf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val newDate: Date = spf.parse(date1)
            spf = SimpleDateFormat("hh:mm aaa")
            date1 = spf.format(newDate)
            return date1
        }

        private fun formateDate2(date: String): String {
            var date1 = date
            var spf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val newDate: Date = spf.parse(date1)
            spf = SimpleDateFormat("EEE")
            date1 = spf.format(newDate)
            return date1
        }
    }
}