package com.example.europrofile.ui.accountpages.myorders.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import com.example.europrofile.domain.Order
import java.text.SimpleDateFormat

class OrderAdapter(private val list : MutableList<Order>): RecyclerView.Adapter<OrderAdapter.OrderVH>() {

    fun refreshList(newList : List<Order>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
        val inflater = LayoutInflater.from(parent.context)
        return OrderVH(inflater.inflate(R.layout.order_vh, parent, false))
    }

    override fun onBindViewHolder(holder: OrderVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class OrderVH(view : View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.title)
        private val name = view.findViewById<TextView>(R.id.edit_creation_order)
        private val phone = view.findViewById<TextView>(R.id.edit_creation_order2)
        private val mail = view.findViewById<TextView>(R.id.edit_creation_order3)
        private val date = view.findViewById<TextView>(R.id.edit_creation_order4)
        private val time = view.findViewById<TextView>(R.id.edit_creation_order5)
        private val comment = view.findViewById<TextView>(R.id.edit_creation_order6)

        fun onBind(order : Order) {
            val format = SimpleDateFormat("dd.MM.yyyy")
            val titleText = "Заявка от " + format.format(order.orderDate?:"")
            title.text = titleText
            name.text = order.name
            phone.text = order.phoneNumber
            mail.text = order.email
            date.text = order.date
            time.text = order.time
            comment.text = order.comments
        }
    }
}