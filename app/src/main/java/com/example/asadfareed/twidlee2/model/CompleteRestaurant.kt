package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class CompleteRestaurant(
    val id : Int,
    val name : String,
    val address : String,
    val longitude : Double,
    val latitude : Double,
    val zip_code : String,
    val area : String,
    val city : String,
    val state : String,
    val country : String,
    val business_contact : String,
    val business_contact_1 : String,
    val created_on : String,
    val admin : Admin,
    val status : String,
    val business_email : String,
    val created_by : String,
    val is_featured : Boolean,
    val cuisine_types : List<String>,
    val food_types : List<String>,
    val rating : Double,
    val deals : List<Deals>,
    val is_favorite : Boolean,
    val cover_image : String
):Serializable {
}

data class Admin (

    val id : Int,
    val user_name : String,
    val phone : String,
    val name : String,
    val date_joined : String,
    val user_type : Int,
    val is_push_notification : Boolean,
    val is_email_notification : Boolean,
    val is_active : Boolean,
    val phone_verified : Boolean,
    val profile_picture : String
)