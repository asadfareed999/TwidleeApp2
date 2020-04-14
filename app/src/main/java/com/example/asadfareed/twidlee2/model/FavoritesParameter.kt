package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class FavoritesParameter(
    val restaurant : Int,
    val is_favorite : Boolean
) :Serializable {
}