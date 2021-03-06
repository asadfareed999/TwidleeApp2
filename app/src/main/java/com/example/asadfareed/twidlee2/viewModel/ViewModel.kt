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
import com.example.asadfareed.twidlee2.activity.UserManagementActivity
import com.example.asadfareed.twidlee2.database.dao.DealDao
import com.example.asadfareed.twidlee2.database.db.DealDatabase
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.fragments.CodeVerificationFragment
import com.example.asadfareed.twidlee2.fragments.EditProfileFragment
import com.example.asadfareed.twidlee2.fragments.LoginFragment
import com.example.asadfareed.twidlee2.model.*
import com.example.asadfareed.twidlee2.repository.DealRepository
import com.example.asadfareed.twidlee2.utils.retrofitInstance
import com.example.asadfareed.twidlee2.utils.utils
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_forgot_password.view.*
import kotlinx.android.synthetic.main.fragment_forgot_password.view.phone_number_profile
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ViewModel : ViewModel() {

    private lateinit var dealDao: DealDao
    private var dealsList: MutableLiveData<ArrayList<Deal>>
    private var deals: ArrayList<DealRoom>
    private lateinit var retrofit: Retrofit
    private var user: MutableLiveData<User>
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var context: Context
    private lateinit var repository: DealRepository

    init {
        dealsList = MutableLiveData()
        deals= ArrayList()
        user = MutableLiveData()
        Log.i("Deals View Model", " Deals View Model Created....")
    }

    fun getDeals(activity: FragmentActivity?): MutableLiveData<ArrayList<Deal>> {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        loadDeals()
        return dealsList
    }

    fun getRepoDeals(activity: FragmentActivity?): ArrayList<DealRoom> {
        context = activity!!
        val database: DealDatabase? = DealDatabase.getInstance(context)
        if (database != null) {
            dealDao = database.dealDao()
        }
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        repository= DealRepository(
            executor = executorService,
            dealDao = dealDao
        )
        deals =repository.getDeal(context as FragmentActivity)
        return deals
    }

    private fun loadDeals() {
        val api: API = retrofit.create(API::class.java)
        val call: Call<ArrayList<Deal>> = api.deals1
        call.enqueue(object : Callback<ArrayList<Deal>> {
            override fun onResponse(
                call: Call<ArrayList<Deal>>,
                response: Response<ArrayList<Deal>>
            ) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response  " + response.code())
                //  Log.i("Response","Response : "+response.body())
                dealsList.setValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Deal>>, t: Throwable) {

            }
        })
    }

    public fun login(login: Login, activity: FragmentActivity?) {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<User>? = api.createUser(login)
        call!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response " + response.code())
                //Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code() == 200) {
                    user.setValue(response.body())
                    saveCredentials(activity)
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                } else if (response.code()==400){
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
                    utils.logOut(activity)

                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity, "Login Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun saveCredentials(activity: FragmentActivity?) {
        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        user.observe(activity, Observer(function = fun(user: User?) {
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
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<User>? = api.registerUser(signUp)
        call!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                Log.i("Response", "Response : " + response.body())
                if (response.isSuccessful && response.code() == 200) {
                    //Toast.makeText(activity, "Sign Up Successful  ", Toast.LENGTH_LONG).show()
                   //user.setValue(response.body())
                    //saveCredentials(activity)
                    utils.loadFragment(CodeVerificationFragment(response.body()), activity)
                } else if (response.code()==400){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),Error::class.java)
                    Toast.makeText(activity, mError.message, Toast.LENGTH_LONG).show()
                }else if (response.code()==500){
                   /* val gson = GsonBuilder().create()
                    var mError =
                        gson.fromJson(response.errorBody()!!.string(),InvalidToken::class.java)*/
                    Toast.makeText(activity, "Email/Phone already exists", Toast.LENGTH_LONG).show()
                }else if (response.code()==403){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),InvalidToken::class.java)
                    Toast.makeText(
                        activity,
                        mError.detail,
                        Toast.LENGTH_LONG
                    ).show()
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity, "Sign Up Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    public fun verifyRegistration(
        activity: FragmentActivity?,
        verifyCode: VerifyCode,
        user1: User
    ) {
        context = activity!!
        user.value=user1
        saveCredentials(activity)
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        sharedPref = activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.registerCodeVerification(verifyCode)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                if (response.isSuccessful && response.code() == 200) {
                    Toast.makeText(activity, "Phone number verified ", Toast.LENGTH_LONG).show()
                    //utils.loadFragment(LoginFragment(),activity)
                    activity.startActivity(Intent(activity,MainActivity::class.java))
                    activity.finish()
                } else if (response.code()==400){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),Error::class.java)
                    Toast.makeText(activity, mError.message, Toast.LENGTH_LONG).show()
                    sharedPref.edit().clear().apply()
                }else if (response.code()==403){
                    val gson = GsonBuilder().create()
                    val mError =
                        gson.fromJson(response.errorBody()!!.string(),InvalidToken::class.java)
                    Toast.makeText(
                        activity,
                        mError.detail,
                        Toast.LENGTH_LONG
                    ).show()
                    sharedPref.edit().clear().apply()
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                    sharedPref.edit().clear().apply()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed. Try again ", Toast.LENGTH_LONG).show()
                sharedPref.edit().clear().apply()
            }
        })

    }

    public fun resendCode(
        activity: FragmentActivity?,
        user: User
    ) {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.resendCode()
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                if (response.isSuccessful && response.code() == 200) {
                    Toast.makeText(activity, "Code Sent ", Toast.LENGTH_LONG).show()
                    utils.loadFragment(CodeVerificationFragment(user), activity)
                } else if (response.code()==400){
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    public fun logout(activity: FragmentActivity) {
        context = activity
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        sharedPref = activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.logout()
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                Log.i("Response", "Response : " + response.body())
                if (response.isSuccessful && response.code() == 200) {
                    Toast.makeText(activity, "Logout Successful  ", Toast.LENGTH_LONG).show()
                    sharedPref.edit().clear().apply()
                    activity.startActivity(Intent(activity, UserManagementActivity::class.java))
                    activity.finish()
                } else if (response.code()==400){
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Logout Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })
    }

    public fun getCode(
        activity: FragmentActivity,
        getCode: GetCode,
        view: View
    ) {
        context = activity
        //val view1: View = view
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        //val code: String = ""
        sharedPref = activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.getCode(getCode)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                Log.i("Response", "Response : " + response.body())
                if (response.isSuccessful && response.code() == 200) {
                    // Toast.makeText(activity,"Success : "+response.message(), Toast.LENGTH_LONG).show()
                    view.buttonSendConfirmForgotPassword.text =
                        context.getString(R.string.hint_confirm)
                    view.phone_number_profile.isEnabled = false
                    view.phone_number_profile.isFocusable = false
                    view.viewVerificationCodeForgotPassword.visibility = View.VISIBLE
                } else if (response.code()==400){
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })
    }



    public fun updatePassword(
        activity: FragmentActivity?,
        updatePassword: UpdatePassword
    ) {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.updatePassword(updatePassword)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                //Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code() == 200) {
                    Toast.makeText(activity, "Password Updated ", Toast.LENGTH_LONG).show()
                    utils.loadFragment(LoginFragment(), activity)
                } else if (response.code()==400){
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    public fun updateProfile(
        activity: FragmentActivity?,
        updateProfile: UpdateProfile
    ) {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<User>? = api.updateProfile(updateProfile)
        call!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                //Log.i("Response","Response : "+response.body())
                if (response.isSuccessful && response.code() == 200) {
                    Toast.makeText(activity, "Profile Updated ", Toast.LENGTH_LONG).show()
                        user.setValue(response.body())
                        saveCredentials(activity)
                    if (updateProfile.is_email_notification==null && updateProfile.is_push_notification==null) {
                        utils.loadFragment2(EditProfileFragment("Profile"), context as FragmentActivity)
                        // loadFragment2(AccountFragment(), activity)
                    }
                } else if (response.code()==400){
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity, "Failed... Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }

    public fun getCodeUpdatePhone(
        activity: FragmentActivity,
        getCode: GetCode,
        view: View
    ) {
        context = activity
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        sharedPref = activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.getCodeUpdatePhone(getCode)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                Log.i("Response", "Response : " + response.body())
                if (response.isSuccessful && response.code() == 200) {
                   // Toast.makeText(activity,"Success "+response.message(), Toast.LENGTH_LONG).show()
                    view.fragment_textInputLayout_mobileNumber_profile.isEnabled = false
                    view.fragment_textInputLayout_mobileNumber_profile.isFocusable = false
                    view.viewVerificationCode.visibility = View.VISIBLE
                    view.buttonEditSaveProfile.text = "Update Profile"
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed.... Try again ", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun changePassword(
        activity: FragmentActivity?,
        changePassword: ChangePassword
    ) {
        context = activity!!
        retrofit=retrofitInstance.getRetrofitInstance(activity)
        val api: API = retrofit.create(API::class.java)
        val call: Call<ResponseBody>? = api.changePassword(changePassword)
        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //finally we are setting the list to our MutableLiveData
                Log.i("Response", "Response : " + response.code())
                if (response.isSuccessful && response.code() == 200) {
                    Toast.makeText(activity, "Password Changed ", Toast.LENGTH_LONG).show()
                } else if (response.code()==400){
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
                    utils.logOut(activity)
                }else {
                    Toast.makeText(activity, "Error  " + response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, "Failed. Try again ", Toast.LENGTH_LONG).show()
            }
        })

    }






}