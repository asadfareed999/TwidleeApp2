package com.example.asadfareed.retrodealsdemo


import com.example.asadfareed.twidlee2.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST




interface API {

    @get:GET("deals")
    val deals: Call<ArrayList<Deal>>

    @POST("auth/login/")
    fun createUser(@Body login: Login?): Call<Login>?

    @POST("auth/customer_registration/")
    fun registerUser(@Body signUp: SignUp): Call<Login>?

    @POST("logout/")
    fun logout(): Call<ResponseBody>?

    @POST("auth/code_for_password/")
    fun getCode(@Body getCode: GetCode): Call<ResponseBody>?

    @POST("auth/update_password/")
    fun updatePassword(@Body updatePassword: UpdatePassword): Call<ResponseBody>?

    @POST("auth/update_password/")
    fun updateProfile(@Body updateProfile: UpdateProfile): Call<ResponseBody>?

    companion object {
        val BASE_URL = "http://52.73.221.180/api/v1/"
    }
}