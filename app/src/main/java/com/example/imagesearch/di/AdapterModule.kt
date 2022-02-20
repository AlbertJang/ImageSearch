package com.example.imagesearch.di

import com.example.imagesearch.presentation.view.main.ImageAdapter
import com.example.imagesearch.presentation.view.main.paging.FooterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class AdapterModule {
    @Provides
    @ActivityScoped
    fun provideImageAdapter(): ImageAdapter = ImageAdapter()

    @Provides
    @ActivityScoped
    fun provideFooterAdapter(): FooterAdapter = FooterAdapter()
}