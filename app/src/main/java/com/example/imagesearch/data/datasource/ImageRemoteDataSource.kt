package com.example.imagesearch.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearch.data.api.SearchService
import com.example.imagesearch.data.model.ImageModel
import com.example.imagesearch.data.model.toModel

class ImageRemoteDataSource(
    private val service: SearchService,
    private val query: String
) :
    PagingSource<Int, ImageModel>() {
    override fun getRefreshKey(state: PagingState<Int, ImageModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageModel> {
        return try {
            val page = params.key ?: 1
            val result = service.getImages(query = query, page = page, size = 30)
            val nextPage = if (result.meta.isEnd) null else page + 1
            val prevPage = if (page > 1) page - 1 else null

            LoadResult.Page(
                data = result.documents.toModel(),
                nextKey = nextPage,
                prevKey = prevPage
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}