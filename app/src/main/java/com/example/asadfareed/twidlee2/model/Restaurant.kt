package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class Restaurant(
    val id : Int,
    val name : String,
    val cover_image : String,
    val is_favorite : Boolean,
    val image_galeries : List<Image_galeries>,
    val address : Address2,
    val cuisines : List<String>,
    val food_types : List<String>,
    val rating_summary : Rating_summary,
    val business_hours : List<Business_hours>,
    val menu : Menu,
    val rating : Double,
    val deals : List<Deals>
    ):Serializable

data class Address2 (

    val display_address : String,
    val area : String,
    val longitude : Double,
    val latitude : Double,
    val business_contact : String,
    val business_contact_1 : String,
    val business_email : String
):Serializable

data class Image_galeries (

    val id : Int,
    val image_url : String,
    val thumbnail_url : String
):Serializable

data class Rating_summary (

    val your_last_rating : Double,
    val last_ratting_date : String,
    val total_ratings : Double,
    val overall : Double,
    val service : Double,
    val food_quality : Double,
    val cleanliness : Double,
    val price : Double,
    val ratings : List<Ratings>
):Serializable

data class Ratings (

    val id : Int,
    val user_name : String,
    val user_picture : String,
    val overall : Double,
    val comments : String
):Serializable

data class Business_hours (

    val id : Int,
    val day : String,
    val slots : List<Slots>
):Serializable

data class Slots (

    val id : Int,
    val time_start : String,
    val time_end : String
):Serializable

data class Menu (

    val term_one : String,
    val term_two : String,
    val menu_categories : List<Menu_categories>
):Serializable

data class Menu_categories (

    val id : Int,
    val order : Int,
    val name : String,
    val menu_items : List<Menu_items>
):Serializable

data class Menu_items (

    val id : Int,
    val title : String,
    val description : String,
    val price : Double
):Serializable

data class Deals (

    val id : Int,
    val meal_types : List<String>,
    val exclusions : List<String>,
  //  val reservation : Reservation,
    val remaining_seats : Int,
    val remaining_takeaways : Int,
    val dine_in_slots : List<Dine_in_slots>,
    val takeaway_slots : List<Takeaway_slots>,
    val post_time : String,
    val remove_time : String,
    val start_time : String,
    val end_time : String,
    val number_of_seats : Int,
    val seats_per_reservation : Int,
    val number_of_seats_in_thirty_minutes : Int,
    val title : String,
    val discount_type : String,
    val percentage : Int,
    val is_active : Boolean,
    val created_on : String,
    val updated_on : String,
    val description : String,
    val is_dine_in : Boolean,
    val is_take_away : Boolean,
    val is_broadcasted : Boolean,
    val take_away_limit : Int,
    val table_time_limit : Int,
    val phone : String,
    val status : Int,
    val deal_scheduling : Int,
    val posted_by : Int
):Serializable

data class Dine_in_slots (

    val key : String,
    val slot : String
):Serializable

data class Takeaway_slots (

    val key : String,
    val slot : String
):Serializable

/*data class Reservation (
    val reserv:String=""
):Serializable*/
