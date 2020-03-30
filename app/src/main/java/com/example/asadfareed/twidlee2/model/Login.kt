package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class Login(
    var email:String,
    var password:String="",
    var phone:String,
    var name:String,
    var token:String,
    var user_type:Int,
    var user_name:String,
    var phone_verified:Boolean,
    var profile_picture:String,
    var is_push_notification:Boolean,
    var is_email_notification:Boolean
):Serializable{


}