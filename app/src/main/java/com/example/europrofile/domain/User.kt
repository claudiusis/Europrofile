package com.example.europrofile.domain

import android.net.Uri

data class User (
    val id: String,
    var email: String,
    var name: String = "User",
    var number: String = "+7 (***) *** **-**",
    var password: String = "1234",
    var address: String = "Не определён",
    var imgUri : Uri? = null)