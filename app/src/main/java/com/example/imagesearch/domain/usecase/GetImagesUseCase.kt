package com.example.imagesearch.domain.usecase

import androidx.paging.PagingData
import com.example.imagesearch.data.model.ImageModel
import com.example.imagesearch.domain.repository.ImagePagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository: ImagePagingRepository) {
    operator fun invoke(query: String): Flow<PagingData<ImageModel>> =
        repository.searchImage(query = query)
}