package com.example.asadfareed.twidlee2.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.asadfareed.retrodealsdemo.API
import com.example.asadfareed.twidlee2.database.dao.DealDao
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.model.Deal
import com.example.asadfareed.twidlee2.model.Error
import com.example.asadfareed.twidlee2.model.InvalidToken
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DealRepository @Inject constructor(
    // Simple in-memory cache. Details omitted for brevity.
    private val executor: Executor,
    private var dealDao: DealDao
) {
    private lateinit var deals2: java.util.ArrayList<DealRoom>
    private lateinit var sharedPref: SharedPreferences
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var retrofit: Retrofit
    private lateinit var context1:Activity
    private lateinit var dealsList: ArrayList<Deal>

    init {
        Log.i("Repository","Repository created.....")
    }

    fun getDeal(context: FragmentActivity): ArrayList<DealRoom> {
        context1=context
        refreshUser()
        deals2=ArrayList()
            // Returns a LiveData object directly from the database.
            deals2 = dealDao.load().toCollection(ArrayList<DealRoom>())
        val size:Int=deals2.size

        /* context1.runOnUiThread {
             Toast.makeText(context1,"hey",Toast.LENGTH_LONG).show()
         }*/
        return deals2
    }

    private fun refreshUser() {
        // Runs in a background thread.
        // executor.execute {
        // Check if user data was fetched recently.
        // val userExists = dealDao.hasDeal(FRESH_TIMEOUT)
/*
            if (!userExists) {
*/
        getRetrofitInstance()
        loadDeals()
        /* }*/
            //}
    }

    private fun loadDeals() {
        val api: API = retrofit.create(API::class.java)
        val call: Call<ArrayList<DealRoom>> = api.deals
        call.enqueue(object : Callback<ArrayList<DealRoom>> {
            override fun onResponse(call: Call<ArrayList<DealRoom>>, response: Response<ArrayList<DealRoom>>) {
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                  executor.execute {
                      dealDao.clear()
                        dealDao.save(response.body()!!)
                   }
                }else if (response.code()==400){
                    val gson = GsonBuilder().create()
                    var mError =
                        gson.fromJson(response.errorBody()!!.string(), Error::class.java)
                    Toast.makeText(context1, mError.message, Toast.LENGTH_LONG).show()
                }else if (response.code()==403){
                    val gson = GsonBuilder().create()
                    var mError =
                        gson.fromJson(response.errorBody()!!.string(), InvalidToken::class.java)
                    Toast.makeText(
                        context1,
                        mError.detail,
                        Toast.LENGTH_LONG
                    ).show()

                }else {
                    Toast.makeText(context1, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onFailure(call: Call<ArrayList<DealRoom>>, t: Throwable) {

            }
        })
    }


    private fun getRetrofitInstance() {
        sharedPref = context1.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val token: String = sharedPref.getString("token_key", "")!!
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("x-api-key", "5f7af37cb35f5cd8")
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(request)
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
    }
    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}