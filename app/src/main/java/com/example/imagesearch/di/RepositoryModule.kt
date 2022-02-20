package com.example.imagesearch.di

import com.example.imagesearch.data.api.SearchService
import com.example.imagesearch.data.repository.ImagePagingRepositoryImpl
import com.example.imagesearch.domain.repository.ImagePagingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideImagePagingRepository(service: SearchService): ImagePagingRepository =
        ImagePagingRepositoryImpl(service)
}