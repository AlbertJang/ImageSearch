package com.example.imagesearch.presentation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.data.model.ImageModel
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.presentation.view.detail.DetailActivity
import com.example.imagesearch.presentation.view.main.paging.FooterAdapter
import com.example.imagesearch.presentation.viewmodel.MainViewModel
import com.example.imagesearch.util.clicks
import com.example.imagesearch.util.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var imageAdapter: ImageAdapter
    @Inject
    lateinit var footerAdapter: FooterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupObserver()
    }

    private fun setupView() {
        imageAdapter.setClickListener {
            startDetail(it)
        }

        binding.run {
            rvImage.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 3)
                adapter = imageAdapter.withLoadStateFooter(footerAdapter)
                setHasFixedSize(true)

            }

            etSearch.textChanges()
                .debounce(2000)
                .filterNot { it.isNullOrBlank() }
                .onEach {
                    viewModel.setQuery(it.toString())
                    imageAdapter.refresh()
                }.launchIn(lifecycleScope)

            btnRetry.clicks().debounce(1000).onEach {
                imageAdapter.retry()
            }.launchIn(lifecycleScope)

            refreshLayout.setOnRefreshListener {
                if(imageAdapter.itemCount == 0) refreshLayout.isRefreshing = false
                imageAdapter.refresh()
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            imageAdapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error) {
                    // error
                    binding.run {
                        rvImage.isVisible = false
                        tvEmpty.isVisible = false
                        groupError.isVisible = true
                    }

                } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && imageAdapter.itemCount < 1) {
                    // empty view
                    binding.run {
                        rvImage.isVisible = false
                        tvEmpty.isVisible = true
                        groupError.isVisible = false
                    }
                } else {
                    // normal
                    binding.run {
                        rvImage.isVisible = true
                        tvEmpty.isVisible = false
                        groupError.isVisible = false
                    }
                }

                binding.refreshLayout.apply {
                    if(isRefreshing) isRefreshing = false
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.searchList.distinctUntilChanged().collect {
                imageAdapter.submitData(it)
            }
        }
    }

    private fun startDetail(item: ImageModel?) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra("item", item)
            startActivity(this)
        }
    }
}