package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class UpdateProfile(
    var phone:String?=null,
    var code:String?=null,
    var name:String?=null,
    var profile_picture:String?=null,
    var is_push_notification:Boolean?=null,
    var is_email_notification:Boolean?=null
):Serializable {
}