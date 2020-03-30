package com.example.asadfareed.twidlee2.model

import java.io.Serializable

data class SignUp (
    var email:String,
    var password:String,
    var phone:String,
    var name:String
): Serializable