package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesearch.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getImagesUseCase: GetImagesUseCase) :
    ViewModel() {
    private val queryFlow = MutableStateFlow("")

    fun setQuery(query: String) {
        queryFlow.value = query
    }

    @ExperimentalCoroutinesApi
    val searchList = queryFlow.filterNot { it.isBlank() }.flatMapLatest {
        getImagesUseCase(it)
    }.cachedIn(viewModelScope)
}