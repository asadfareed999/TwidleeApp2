package com.example.asadfareed.twidlee2.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.retrodealsdemo.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitInstance {

    companion object {

        private val sharedPrefFile = "kotlinsharedpreference"
        private lateinit var sharedPref: SharedPreferences
        private lateinit var context: Context
        private lateinit var retrofit: Retrofit

        fun getRetrofitInstance(activity: FragmentActivity?): Retrofit {
            context=activity!!
            sharedPref = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            val token: String = sharedPref.getString("token_key", "")!!
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            if (token.isEmpty()) {
                httpClient.addInterceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("x-api-key", "5f7af37cb35f5cd8")
                        .build()

                    chain.proceed(request)
                }
            }else{
                httpClient.addInterceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("x-api-key", "5f7af37cb35f5cd8")
                        .addHeader("Authorization", "Bearer " + token)
                        .build()

                    chain.proceed(request)
                }
            }
            // added logging interceptor
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(httpLoggingInterceptor)

            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .client(httpClient.build())
                .build()
            return retrofit
        }
    }
}