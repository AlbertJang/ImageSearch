package com.example.imagesearch.presentation.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imagesearch.data.model.ImageModel
import com.example.imagesearch.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<ImageModel>("item").run {
            setupView(this)
        }
    }

    private fun setupView(item: ImageModel?) {
        Glide.with(this)
            .load(item?.imageUrl)
            .into(binding.ivImage)

        binding.tvSitename.text = item?.displaySiteName
        binding.tvDatetime.text = item?.datetime
    }
}