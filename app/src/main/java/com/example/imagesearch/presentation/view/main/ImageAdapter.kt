package com.example.imagesearch.presentation.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.data.model.ImageModel
import com.example.imagesearch.databinding.ItemImageBinding

class ImageAdapter : PagingDataAdapter<ImageModel, ImageAdapter.ImageViewHolder>(
    object : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
            oldItem == newItem
    }
) {
    private var clickListener: ((ImageModel?) -> Unit)? = null
    fun setClickListener(listener: ((ImageModel?) -> Unit)) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(
        private val binding: ItemImageBinding,
        private val clickListener: ((ImageModel?) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener?.invoke(item)
            }
        }

        private var item: ImageModel? = null

        fun bind(item: ImageModel?) {
            this.item = item
            Glide.with(binding.ivImage)
                .load(item?.thumbnailUrl)
                .into(binding.ivImage)
        }
    }
}