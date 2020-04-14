package com.example.asadfareed.twidlee2.viewModel

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class FavoritesViewModel : ViewModel() {

    private  var favoritesRestaurant: MutableLiveData<ArrayList<Deal>>
    private lateinit var retrofit: Retrofit


    init {
        favoritesRestaurant = MutableLiveData()
        Log.i("Favorites View Model", " Favorites View Model Created....")
    }

    fun getFavoritesRestaurant(activity: FragmentActivity?): MutableLiveData<ArrayList<Deal>> {
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        loadFavorites(activity!!)
        return favoritesRestaurant
    }

    private fun loadFavorites(activity: FragmentActivity) {
        val api: API = retrofit.create(API::class.java)
        val call:Call<ArrayList<Deal>> = api.getFavorites()
        call.enqueue(object : Callback<ArrayList<Deal>> {
            override fun onResponse(
                call: Call<ArrayList<Deal>> ,
                response: Response<ArrayList<Deal>>
            ) {
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                    //  Log.i("Response","Response : "+response.body())
                    favoritesRestaurant.value=response.body()
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

            override fun onFailure(call: Call<ArrayList<Deal>> , t: Throwable) {
                Toast.makeText(activity, "Failed "+t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }



}