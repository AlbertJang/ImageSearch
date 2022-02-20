package com.example.imagesearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagesearch.data.api.SearchService
import com.example.imagesearch.data.datasource.ImageRemoteDataSource
import com.example.imagesearch.data.model.ImageModel
import com.example.imagesearch.domain.repository.ImagePagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePagingRepositoryImpl @Inject constructor(private val service: SearchService) :
    ImagePagingRepository {

    override fun searchImage(query: String): Flow<PagingData<ImageModel>> {
        return Pager(config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 30,
            initialLoadSize = 30
        ),
            pagingSourceFactory = {
                ImageRemoteDataSource(service, query)
            }).flow
    }
}