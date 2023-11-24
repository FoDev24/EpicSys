package com.example.epicsys.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.epicsys.R
import com.example.epicsys.databinding.AirlinesItemBinding
import com.example.epicsys.domain.model.AirlineItem

class AllAirlinesAdapter  : RecyclerView.Adapter<AllAirlinesAdapter.AllAirlinesViewHolder>() {

    inner class AllAirlinesViewHolder(val binding:AirlinesItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(airline: AirlineItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(R.drawable.logo)
                    .placeholder(R.drawable.loading_icon)
                    .into(allAirlineTvImg)
                tvAllAirlineName.text = airline.name
                Log.d("test", "${airline.logoURL}")
            }
        }

    }

    private val differCallBack = object: DiffUtil.ItemCallback<AirlineItem>(){
        override fun areItemsTheSame(oldItem: AirlineItem, newItem: AirlineItem): Boolean {
            return oldItem.code ==newItem.code
        }

        override fun areContentsTheSame(oldItem: AirlineItem, newItem: AirlineItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAirlinesViewHolder {
        return AllAirlinesViewHolder(
            AirlinesItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: AllAirlinesViewHolder, position: Int) {
        val airline = differ.currentList[position]
        holder.bind(airline)
        holder.itemView.setOnClickListener {
            onAirlineClick?.invoke(airline)
        }
    }

    var onAirlineClick : ((AirlineItem) -> Unit)? = null


}