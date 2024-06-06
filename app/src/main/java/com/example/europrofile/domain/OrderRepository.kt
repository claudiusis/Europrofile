package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun uploadOrder(order : Order) : RequestResult<String>

    suspend fun getOrders(uid : String) : Flow<RequestResult<Order>>

}