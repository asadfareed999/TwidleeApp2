package com.example.asadfareed.twidlee2.viewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.asadfareed.retrodealsdemo.API
import com.example.asadfareed.twidlee2.*
import com.example.asadfareed.twidlee2.fragments.LoginFragment
import com.example.asadfareed.twidlee2.model.*
import kotlinx.android.synthetic.main.fragment_forgot_password.view.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ViewModel : ViewModel() {

    private  var dealsList: MutableLiveData<ArrayList<Deal>>
    private lateinit var retrofit: Retrofit
    private lateinit var user:MutableLiveData<Login>
    private lateinit var userLog:Login
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var context: Context

    init {
        dealsList= MutableLiveData()
        user= MutableLiveData()
        Log.i("Deals View Model"," Deals View Model Created....")
    }

    fun getDeals(activity: FragmentActivity?): MutableLiveData<ArrayList<Deal>> {
        context= activity!!
        getRetrofitInstance()
        loadDeals()
       return dealsList
    }

    private fun loadDeals() {
        val api: API = retrofit.create(API::class.java)
        val call: Call<ArrayList<Deal>> = api.deals
        call.enqueue(object : Callback<ArrayList<Deal>> {
            override fun onResponse(call: Call<ArrayList<Deal>>, response: Response<ArrayList<Deal>>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
              //  Log.i("Response","Response : "+response.body())
                dealsList.setValue(response.body())
            }
            override fun onFailure(call: Call<ArrayList<Deal>>, t: Throwable) {
            }
        })
    }

    public fun login(login: Login, activity: FragmentActivity?) {
        context= activity!!
        getRetrofitInstance()
        val api: API = retrofit.create(API::class.java)
        val call: Call<Login>? = api.createUser(login)
        call!!.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
                //Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code()==200){
                    user.setValue(response.body())
                    saveCredentials(activity)
                    activity!!.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }else if (response.code()==400){
                    Toast.makeText(activity,"Invalid Credentials "+response.message(), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity,"Error :  "+response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Toast.makeText(activity,"Login Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun saveCredentials(activity: FragmentActivity?) {
        sharedPref= activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        user.observe(activity, Observer(function = fun(user: Login?) {
            user?.let {
                editor.putString("email_key", user.email)
                editor.putString("phone_key", user.phone)
                editor.putString("name_key", user.name)
                editor.putString("token_key", user.token)
                editor.putInt("user_type_key", user.user_type)
                editor.putString("user_name_key", user.user_name)
                editor.putBoolean("phone_verified_key", user.phone_verified)
                editor.putString("profile_picture_key", user.profile_picture)
                editor.putBoolean("is_push_notification_key", user.is_push_notification)
                editor.putBoolean("is_email_notification_key", user.is_email_notification)
                editor.apply()
                editor.commit()
            }
        }))
    }

    public fun signUp(signUp: SignUp, activity: FragmentActivity?) {
        context= activity!!
        getRetrofitInstance()
        val api: API = retrofit.create(API::class.java)
        val call: Call<Login>? = api.registerUser(signUp)
        call!!.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
                Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code()==200){
                    Toast.makeText(activity,"Sign Up Successful :  ", Toast.LENGTH_LONG).show()
                    loadFragment(CodeVerificationFragment(),activity)
                }else if (response.code()==400){
                    Toast.makeText(activity,"Invalid Credentials "+response.message(), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity,"Error :  "+response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Toast.makeText(activity,"Sign Up Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    public fun logout(activity: FragmentActivity) {
        context= activity
        getRetrofitInstance()
        sharedPref= activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.logout()
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
                Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code()==200){
                    Toast.makeText(activity,"Logout Successful :  ", Toast.LENGTH_LONG).show()
                    sharedPref.edit().clear().apply()
                    activity.startActivity(Intent(activity,UserManagementActivity::class.java))
                    activity.finish()
                }else{
                    Toast.makeText(activity,"Error :  "+response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity,"Logout Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })
    }

    public fun getCode(
        activity: FragmentActivity,
        getCode: GetCode,
        view: View
    ) {
        context= activity
        val view1:View=view
        getRetrofitInstance()
        val code:String=""
        sharedPref= activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.getCode(getCode)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
                Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code()==200){
                   // Toast.makeText(activity,"Success : "+response.message(), Toast.LENGTH_LONG).show()
                    view.buttonSendConfirmForgotPassword.text=context.getString(R.string.hint_confirm)
                    view.phone_number_profile.isEnabled=false
                    view.phone_number_profile.isFocusable=false
                    view.viewVerificationCodeForgotPassword.visibility=View.VISIBLE
                }else{
                    Toast.makeText(activity,"Error :  "+response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity,"Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })
    }

    public fun updatePassword(
        activity: FragmentActivity?,
        updatePassword: UpdatePassword
    ) {
        context= activity!!
        getRetrofitInstance()
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.updatePassword(updatePassword)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
                //Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code()==200){
                    Toast.makeText(activity,"Password Updated :  ", Toast.LENGTH_LONG).show()
                    loadFragment(LoginFragment(),activity)
                }else{
                    Toast.makeText(activity,"Error :  "+response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity,"Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    public fun updateProfile(
        activity: FragmentActivity?,
        updateProfile: UpdateProfile
    ) {
        context= activity!!
        getRetrofitInstance()
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.updateProfile(updateProfile)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response","Response : "+response.code())
                //Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code()==200){
                    Toast.makeText(activity,"Profile Updated :  ", Toast.LENGTH_LONG).show()
                    loadFragment(AccountFragment(),activity)
                }else{
                    Toast.makeText(activity,"Error :  "+response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity,"Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun getRetrofitInstance() {
        sharedPref=context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val token:String = sharedPref.getString("token_key","")!!
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("x-api-key", "5f7af37cb35f5cd8")
                    .addHeader("Authorization","Bearer "+token)
                    .build()
                chain.proceed(request)
            }
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .client(httpClient.build())
                .build()
    }

    private fun loadFragment(fragment: Fragment, activity: FragmentActivity?) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}