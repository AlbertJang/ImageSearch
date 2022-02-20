package com.example.imagesearch.domain.repository

import androidx.paging.PagingData
import com.example.imagesearch.data.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImagePagingRepository {
    fun searchImage(query: String): Flow<PagingData<ImageModel>>
}