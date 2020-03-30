package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class UpdatePassword(
    val password: String,
    val code:String
    ):Serializable {
}