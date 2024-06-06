package com.example.europrofile.data

import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.Order
import com.example.europrofile.domain.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class OrderRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : OrderRepository {

    override suspend fun uploadOrder(order: Order): RequestResult<String> {
        return try {
            val result =  firestore.collection(FireBaseTags.ORDER).document(order.id).set(
                order
            ).await()
            RequestResult.Success("Запись заполнена")
        } catch (e : Exception) {
            RequestResult.Error(e)
        }
    }

    override suspend fun getOrders(uid: String): Flow<RequestResult<Order>> = flow {
        val orders = firestore.collection(FireBaseTags.ORDER).whereEqualTo("userId", uid).get().await()
        try {
            orders.documents.forEach { doc ->
                val order = doc.toObject(Order::class.java)
                order?.let {
                    emit(RequestResult.Success(it))
                }?: throw Exception("Ошибка получения записи")
            }
        } catch (e : Exception){
            emit(RequestResult.Error(e))
        }
    }
}