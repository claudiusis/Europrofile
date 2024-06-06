package com.example.europrofile.domain

import java.util.Date

data class Order(
    val id : String = "-1",
    val userId : String? = "-1",
    val name : String? = "",
    val phoneNumber : String? = "",
    val email : String? = "",
    val date : String? = "",
    val time : String? = "",
    val address : String? = "",
    val comments : String? = "",
    val orderDate : Date? = null
)
