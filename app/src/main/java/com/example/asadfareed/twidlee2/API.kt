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
    fun createUser(@Body login: Login?): Call<User>?

    @POST("auth/customer_registration/")
    fun registerUser(@Body signUp: SignUp): Call<User>?

    @POST("auth/verify/")
    fun registerCodeVerification(@Body verifyCode: VerifyCode): Call<ResponseBody>?

    @POST("auth/resend_code/")
    fun resendCode(): Call<ResponseBody>?

    @POST("logout/")
    fun logout(): Call<ResponseBody>?

    @POST("auth/code_for_password/")
    fun getCode(@Body getCode: GetCode): Call<ResponseBody>?

    @POST("auth/update_password/")
    fun updatePassword(@Body updatePassword: UpdatePassword): Call<ResponseBody>?

    @POST("update_profile/")
    fun updateProfile(@Body updateProfile: UpdateProfile): Call<User>?

    @POST("get_update_code/")
    fun getCodeUpdatePhone(@Body getCode: GetCode): Call<ResponseBody>?

    @POST("auth/change_password/")
    fun changePassword(@Body changePassword: ChangePassword): Call<ResponseBody>?

    companion object {
        val BASE_URL = "http://52.73.221.180/api/v1/"
    }
}