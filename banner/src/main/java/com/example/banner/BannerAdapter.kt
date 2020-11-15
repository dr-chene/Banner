package com.example.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banner.bean.DataEntry
import com.example.banner.databinding.BannerContentBinding

/**
Created by chene on @date 20-11-13 下午3:29
 **/
class BannerAdapter (
    private val dataEntries: List<DataEntry>,
    private val clickListener: BannerClickListener
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BannerViewHolder(
            BannerContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(dataEntries[position % dataEntries.size], clickListener)
    }

    override fun getItemCount() =
        Int.MAX_VALUE

    class BannerViewHolder(
        private val binding: BannerContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataEntry, clickListener: BannerClickListener){
            binding.bannerContentImg.setOnClickListener {
                clickListener.onClick(it, data)
            }
            binding.apply {
                Glide.with(root)
                    .load(data.image)
                    .centerCrop()
                    .into(bannerContentImg)
                bannerContentTitle.text = data.title
                executePendingBindings()
            }
        }
    }

    interface BannerClickListener{
        fun onClick(view: View, data: DataEntry)
    }
}
