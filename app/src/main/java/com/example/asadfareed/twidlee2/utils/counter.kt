package com.example.asadfareed.twidlee2.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.retrodealsdemo.API
import com.example.asadfareed.twidlee2.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class counter {

    companion object {

         fun startTimer(
            tv: TextView,
            endTime: String,
            startTime: String
        ) {
            val endtime = endTime
            val starttime = startTime
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            // val startTime = "2020-04-16T12:30:00" //Timer date 1
            // val endTime = "2020-04-16T14:30:00" //Timer date 2
            // val startDate: Date = formatter.parse(startTime)
            val endDate: Date = formatter.parse(endtime)
            val startDate: Date = formatter.parse(starttime)
            val currentLong: Long = (Calendar.getInstance().timeInMillis)
            val startLong = startDate.getTime()
            val endLong = endDate.getTime()
            val diff = endLong - currentLong
            if (currentLong >= startLong && currentLong <= endLong) {
                object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val millis = millisUntilFinished
                        var hours: String = TimeUnit.MILLISECONDS.toHours(millis).minus(
                            TimeUnit.DAYS.toHours(
                                TimeUnit.MILLISECONDS.toDays(millis))).toString()
                        var minutes:String=
                            TimeUnit.MILLISECONDS.toMinutes(millis) .minus( TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millis))).toString()
                        var seconds:String=
                            TimeUnit.MILLISECONDS.toSeconds(millis) .minus( TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millis))).toString()
                        if(hours.length==1){
                            hours="0"+hours
                        }
                        if(minutes.length==1){
                            minutes="0"+minutes
                        }
                        if(seconds.length==1){
                            seconds="0"+seconds
                        }
                        tv.text = hours+":"+minutes+":"+seconds
                    }

                    override fun onFinish() {
                        tv.text = tv.context.getString(R.string.deal_expired)
                    }
                }.start()
            }else if (currentLong<startLong){
                tv.text = "--:--:--"
            }else{
                tv.text = tv.context.getString(R.string.deal_expired)
            }
        }

    }
}