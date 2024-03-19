package com.example.europrofile.ui.tabs.comments.recycler

import com.example.europrofile.ui.tabs.main.newsrecycler.Image
import java.util.Date

data class Review(val id: Int, val nameOfUser: String, val date: Date, val imageList: List<Image>, val description: String, val countOfLike: Int, val countOfDislike: Int)