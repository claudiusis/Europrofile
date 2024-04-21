package com.example.europrofile.ui.tabs.comments.recycler

import java.util.Date

data class ViewReview(
    val id: String? = null,
    val idOfUser : String? = "-1",
    val nameOfUser: String? = "",
    val userAvatar : String? = null,
    val date: Date? = null,
    var imageList: List<String> = listOf(),
    val description: String? = "",
    val listOfUserLikes: List<String> = listOf(),
    val listOfUserDisLikes: List<String> = listOf())