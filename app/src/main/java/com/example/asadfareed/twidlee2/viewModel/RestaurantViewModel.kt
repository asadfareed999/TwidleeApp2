package com.example.asadfareed.twidlee2.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.asadfareed.retrodealsdemo.API
import com.example.asadfareed.twidlee2.fragments.restaurant.RestaurantFragment
import com.example.asadfareed.twidlee2.model.*
import com.example.asadfareed.twidlee2.utils.retrofitInstance
import com.example.asadfareed.twidlee2.utils.utils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestaurantViewModel : ViewModel() {

    private  var restaurantDetails: MutableLiveData<Restaurant>
    var restaurantsAll: MutableLiveData<ArrayList<CompleteRestaurant>>
    private lateinit var retrofit: Retrofit
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var context: Context


    init {
        restaurantDetails = MutableLiveData()
        restaurantsAll= MutableLiveData()
        Log.i("Restaurant View Model", " Restaurant View Model Created....")
    }

    fun getRestaurantDetails(
        activity: FragmentActivity?,
        adapterPosition: Int
    ): MutableLiveData<Restaurant> {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        loadRestaurantDetails(activity,adapterPosition)
        return restaurantDetails
    }

    private fun loadRestaurantDetails(
        activity: FragmentActivity,
        adapterPosition: Int
    ) {
        val api: API = retrofit.create(API::class.java)
        val call: Call<Restaurant> = api.restaurantDetails(adapterPosition)
        call.enqueue(object : Callback<Restaurant> {
            override fun onResponse(
                call: Call<Restaurant>,
                response: Response<Restaurant>
            ) {
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                    //  Log.i("Response","Response : "+response.body())
                    restaurantDetails.value=response.body()
                    restaurantDetails.observeForever(Observer (function = fun(restaurant: Restaurant?) {
                            restaurant?.let {
                                utils.loadFragment2(RestaurantFragment(restaurant), activity)
                            }
                        }))
                }else if (response.code()==400){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),Error::class.java)
                    Toast.makeText(activity, mError.message, Toast.LENGTH_LONG).show()
                }else if (response.code()==403){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),InvalidToken::class.java)
                    Toast.makeText(
                        activity,
                        mError.detail,
                        Toast.LENGTH_LONG
                    ).show()

                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                Toast.makeText(activity, "Failed "+t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

     fun getAllRestaurants(
        activity: FragmentActivity
    ){
        context = activity
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ArrayList<CompleteRestaurant>> = api.getAllRestaurants()
        call.enqueue(object : Callback<ArrayList<CompleteRestaurant>> {
            override fun onResponse(
                call: Call<ArrayList<CompleteRestaurant>>,
                response: Response<ArrayList<CompleteRestaurant>>
            ) {
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                    //  Log.i("Response","Response : "+response.body())
                    restaurantsAll.value=response.body()
                }else if (response.code()==400){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),Error::class.java)
                    Toast.makeText(activity, mError.message, Toast.LENGTH_LONG).show()
                }else if (response.code()==403){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),InvalidToken::class.java)
                    Toast.makeText(
                        activity,
                        mError.detail,
                        Toast.LENGTH_LONG
                    ).show()

                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CompleteRestaurant>>, t: Throwable) {
                Toast.makeText(activity, "Failed "+t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }


}