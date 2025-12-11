package org.geeksforgeeks.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brewbuddy.R
import com.example.brewbuddy.data.local.database.entities.OrderHistory
import com.google.android.material.card.MaterialCardView

class Adapter(private var list: List<OrderHistory>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var expandedPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.imgProduct)
        var orderQuantity: TextView = view.findViewById(R.id.orderQuantity_tv)
        var orderName: TextView = view.findViewById(R.id.orderName_tv)
        var date: TextView = view.findViewById(R.id.date_tv)
        var detailsText: TextView = view.findViewById(R.id.details_tv)
        var expandedLayout: View = view.findViewById(R.id.expandedLayout)
        var cardView: MaterialCardView = view.findViewById(R.id.cardView)

        var totalPrice: TextView = view.findViewById(R.id.totalPrice_tv)
        var promo: TextView = view.findViewById(R.id.promo_tv)
        var deliveryFee: TextView = view.findViewById(R.id.deliveryFee_tv)
        var packagingFee: TextView = view.findViewById(R.id.packagingFee_tv)
        var address: TextView = view.findViewById(R.id.address_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_history_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val context = holder.itemView.context

        holder.orderQuantity.text = "${item.quantity}x "
        holder.orderName.text = item.title
        holder.date.text = item.date

        holder.totalPrice.text = " ${String.format("%.3f", item.totalPrice)} RP"
        holder.promo.text = "-${item.promo} RP"
        holder.deliveryFee.text = "${item.deliveryFee} RP"
        holder.packagingFee.text = "${item.packagingFee} RP"
        holder.address.text = item.address

        Glide.with(context)
            .load(item.image)
            .placeholder(R.drawable.sample)
            .error(R.drawable.sample)
            .into(holder.imageView)

        val isExpanded = position == expandedPosition
        holder.expandedLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.detailsText.text = if (isExpanded) context.getString(R.string.hide_details)
        else context.getString(R.string.details)

        holder.detailsText.setOnClickListener {
            val previousExpanded = expandedPosition
            expandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpanded)
            notifyItemChanged(position)
        }

        holder.cardView.setOnClickListener {
            val previousExpanded = expandedPosition
            expandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpanded)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = list.size

    fun updateList(newList: List<OrderHistory>) {
        list = newList
        expandedPosition = -1
        notifyDataSetChanged()
    }
}