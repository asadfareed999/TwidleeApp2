package com.example.asadfareed.twidlee2.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DealRoom (
    @PrimaryKey
    var id:Int,
    var restaurant_name:String,
    var restaurant_id:Int,
    var address: Address,
    var cuisines:List<String> = emptyList(),
    var food_types:List<String> = emptyList(),
    var rating:Double,
    var additional_offers:Int,
    var is_favorite:Boolean,
    var status:String,
    var cover_image:String,
    var remaining_seats:Int,
    var remaining_takeaways:Int,
    var post_time:String,
    var remove_time:String,
    var start_time:String,
    var end_time:String,
    var number_of_seats:Int,
    var seats_per_reservation:Int,
    var number_of_seats_in_thirty_minutes:Int,
    var title:String,
    var discount_type:String,
    var percentage:Double,
    var is_active:Boolean,
    var created_on:String,
    var updated_on:String,
    var description:String,
    var is_dine_in:Boolean,
    var is_take_away:Boolean,
    var is_broadcasted:Boolean,
    var take_away_limit:Int,
    var table_time_limit:Int,
    var phone:String,
    var restaurant:Int,
    var posted_by:Int,
    var exclusions:List<String> = emptyList(),
    var meal_types:List<String> = emptyList()
): Serializable

data class Address(
    var display_address:String,
    var area:String,
    var longitude:Double,
    var latitude:Double,
    var business_contact:String,
    var business_contact_1:String,
    var business_email:String
):Serializable

