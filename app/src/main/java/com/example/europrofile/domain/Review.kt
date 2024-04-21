package com.example.europrofile.domain

import java.util.Date

data class Review(
    val id: String? = null,
    val idOfUSer: String = "-1",
    val date: Date? = null,
    var imageList: List<String> = listOf(),
    val description: String = "",
    val listOfUserLikes: List<String> = listOf(),
    val listOfUserDisLikes: List<String> = listOf())