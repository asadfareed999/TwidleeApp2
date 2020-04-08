package com.example.asadfareed.twidlee2.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.asadfareed.retrodealsdemo.API
import com.example.asadfareed.twidlee2.model.*
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
    private lateinit var retrofit: Retrofit
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var context: Context


    init {
        restaurantDetails = MutableLiveData()
        Log.i("Restaurant View Model", " Restaurant View Model Created....")
    }

    fun getRestaurantDetails(
        activity: FragmentActivity?,
        adapterPosition: Int
    ): MutableLiveData<Restaurant> {
        context = activity!!
        getRetrofitInstance()
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
                    /*restaurantDetails.observeForever(Observer(function = fun(details: ArrayList<Restaurant>?) {
                        details?.let {
                            Log.i("ResponseSize", "ResponseSize  " + details.size)
                        }
                    }))*/
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

            }
        })
    }

    private fun getRetrofitInstance() {
        sharedPref = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
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

}