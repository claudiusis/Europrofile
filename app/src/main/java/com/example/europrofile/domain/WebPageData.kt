package com.example.europrofile.domain

data class WebPageData(
    val title : String = "",
    val imageUrls : List<String> = listOf(),
    val details : LinkedHashMap<String, String>,
    val price : String = "",
    val description : String = ""
)
