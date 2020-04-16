package com.example.asadfareed.twidlee2.viewModel

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asadfareed.retrodealsdemo.API
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.adapter.FavoritesAdapter
import com.example.asadfareed.twidlee2.database.dao.DealDao
import com.example.asadfareed.twidlee2.database.db.DealDatabase
import com.example.asadfareed.twidlee2.fragments.DealsFragment
import com.example.asadfareed.twidlee2.model.*
import com.example.asadfareed.twidlee2.utils.retrofitInstance
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoritesViewModel : ViewModel() {

    var favoritesRestaurant: MutableLiveData<ArrayList<Deal>>
    private  var favoriteResponse: MutableLiveData<Favorites>
    private lateinit var retrofit: Retrofit
    private lateinit var dealDao: DealDao


    init {
        favoritesRestaurant = MutableLiveData()
        favoriteResponse= MutableLiveData()
        Log.i("Favorites View Model", " Favorites View Model Created....")
    }

    fun getFavoritesRestaurant(
        activity: FragmentActivity?,
        view: View
    ): MutableLiveData<ArrayList<Deal>> {
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        loadFavorites(activity!!,view)
        return favoritesRestaurant
    }

    private fun loadFavorites(
        activity: FragmentActivity,
        view: View
    ) {
        val api: API = retrofit.create(API::class.java)
        val call:Call<ArrayList<Deal>> = api.getFavorites()
        call.enqueue(object : Callback<ArrayList<Deal>> {
            override fun onResponse(
                call: Call<ArrayList<Deal>> ,
                response: Response<ArrayList<Deal>>
            ) {
                var success=false
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                    //  Log.i("Response","Response : "+response.body())
                    favoritesRestaurant.value=response.body()
                    success=true
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
                /*if (success==false){
                    view.reloadView2.visibility=View.VISIBLE
                    view.reloadView2.text=view.context.getString(R.string.reload)
                }*/
            }

            override fun onFailure(call: Call<ArrayList<Deal>> , t: Throwable) {
                Toast.makeText(activity, "Failed "+t.message, Toast.LENGTH_LONG)
                    .show()
                view.reloadView2.visibility=View.VISIBLE
                view.reloadView2.text=view.context.getString(R.string.reload)
            }
        })
    }

    fun makeFavorite(
        activity: FragmentActivity?,
        favoritesParameter: FavoritesParameter,
        markFavorite: ImageButton,
        contextFragment: DealsFragment
    ){
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call:Call<Favorites> = api.makeFavoriteUnfavorite(favoritesParameter)
        var success=favoritesParameter.is_favorite
        call.enqueue(object : Callback<Favorites> {
            override fun onResponse(
                call: Call<Favorites> ,
                response: Response<Favorites>
            ) {
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                    favoriteResponse.value=response.body()
                    markFavorite.isSelected=success
                    val database: DealDatabase? = DealDatabase.getInstance(activity!!)
                    if (database != null) {
                        dealDao = database.dealDao()
                    }
                    val executorService: ExecutorService = Executors.newSingleThreadExecutor()
                    executorService.execute{
                        dealDao.update(favoritesParameter.restaurant,favoritesParameter.is_favorite)
                        activity.runOnUiThread {
                            contextFragment.observeDeals()
                        }
                    }
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

            override fun onFailure(call: Call<Favorites> , t: Throwable) {
                Toast.makeText(activity, "Failed "+t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun makeFavoriteFragment(
        activity: FragmentActivity?,
        favoritesParameter: FavoritesParameter,
        markFavorite: ImageButton,
        dealsList1: ArrayList<Deal>,
        favoritesAdapter: FavoritesAdapter,
        position: Int
    ){
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call:Call<Favorites> = api.makeFavoriteUnfavorite(favoritesParameter)
        var success=favoritesParameter.is_favorite
        call.enqueue(object : Callback<Favorites> {
            override fun onResponse(
                call: Call<Favorites> ,
                response: Response<Favorites>
            ) {
                if (response.code()==200) {
                    Log.i("Response", "Response  " + response.code())
                    favoriteResponse.value=response.body()
                    markFavorite.isSelected=success
                    val database: DealDatabase? = DealDatabase.getInstance(activity!!)
                    if (database != null) {
                        dealDao = database.dealDao()
                    }
                    val executorService: ExecutorService = Executors.newSingleThreadExecutor()
                    executorService.execute{
                        dealDao.update(favoritesParameter.restaurant,favoritesParameter.is_favorite)
                        activity.runOnUiThread {
                            dealsList1.removeAt(position)
                            favoritesAdapter.notifyDataSetChanged()
                        }
                    }
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

            override fun onFailure(call: Call<Favorites> , t: Throwable) {
                Toast.makeText(activity, "Failed "+t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }



}