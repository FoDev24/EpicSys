package com.example.epicsys.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.epicsys.R
import com.example.epicsys.databinding.AirlinesItemBinding
import com.example.epicsys.databinding.FavItemBinding
import com.example.epicsys.domain.model.AirlineItem
import com.example.epicsys.presentation.favorite.FavoriteViewModel

class FavAirlinesAdapter(private val viewModel :FavoriteViewModel) : RecyclerView.Adapter<FavAirlinesAdapter.FavAirlinesViewHolder>() {

    inner class FavAirlinesViewHolder(val binding: FavItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(airline: AirlineItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(R.drawable.logo)
                    .placeholder(R.drawable.loading_icon)
                    .into(favImage)
                favAirlineName.text = airline.name
                favAirlineSite.text = airline.site
                favAirlineCode.text = airline.code


                Log.d("test", "${airline.logoURL}")
            }
        }

    }

    private val differCallBack = object : DiffUtil.ItemCallback<AirlineItem>() {
        override fun areItemsTheSame(oldItem: AirlineItem, newItem: AirlineItem): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: AirlineItem, newItem: AirlineItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAirlinesViewHolder {
        return FavAirlinesViewHolder(
            FavItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: FavAirlinesViewHolder, position: Int) {
        val airline = differ.currentList[position]
        holder.bind(airline)
        holder.itemView.setOnClickListener {
            onAirlineClick?.invoke(airline)
        }
    }

    var onAirlineClick: ((AirlineItem) -> Unit)? = null


}
