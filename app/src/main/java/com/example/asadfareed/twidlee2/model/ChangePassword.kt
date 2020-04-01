package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class ChangePassword(
    var password:String,
    var new_password:String
):Serializable {
}