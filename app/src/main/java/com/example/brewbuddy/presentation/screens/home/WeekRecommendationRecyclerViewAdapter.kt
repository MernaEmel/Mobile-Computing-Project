package com.example.brewbuddy.presentation.screens.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.brewbuddy.R
import com.example.brewbuddy.domain.model.Coffee

class WeekRecommendationRecyclerViewAdapter(
    private val dataSet: List<Coffee>, private val onItemClick: (Coffee) -> Unit
) :
    RecyclerView.Adapter<WeekRecommendationRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTv: TextView = itemView.findViewById(R.id.week_recommendation_item_title)
        val priceTv: TextView = itemView.findViewById(R.id.week_recommendation_item_price)
        val imageIv: ImageView = itemView.findViewById(R.id.week_recommendation_item_image)
        val card: CardView = itemView.findViewById(R.id.week_recommendation_item_card)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.weeks_recommendation_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.titleTv.text = item.title
        viewHolder.priceTv.text = "Rp ${String.format("%.3f", item.price)}"
        viewHolder.card.setOnClickListener {
            onItemClick(item)
        }
        Glide.with(viewHolder.itemView)
            .load(item.image)
            .placeholder(R.drawable.coffee_image_placeholder)
            .transition(withCrossFade(300))
            .into(viewHolder.imageIv)
    }

    override fun getItemCount() = dataSet.size

}