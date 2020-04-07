package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class Error(
    var errorCode:Double,
    var errorDetails:String,
    var message:String
    ):Serializable {
}