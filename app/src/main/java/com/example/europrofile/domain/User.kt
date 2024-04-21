package com.example.europrofile.domain

data class User (
    val id: String = "-1",
    var email: String = "",
    var name: String = "User",
    var number: String = "+7 (***) *** **-**",
    var password: String = "1234",
    var address: String = "Не определён",
    var imgUri : String? = null,
    var countOfReviews :Int = 0,
    var countOfOrders : Int = 0)