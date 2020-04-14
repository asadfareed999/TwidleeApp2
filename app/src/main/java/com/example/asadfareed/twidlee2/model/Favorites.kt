package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class Favorites(
    val id : Int,
    val created_on : String,
    val updated_on : String,
    val restaurant : Int,
    val user : Int
):Serializable {
}