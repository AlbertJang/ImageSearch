package com.example.imagesearch.data.api

import com.example.imagesearch.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {
    @Headers("Authorization: KakaoAK $TOKEN")
    @GET("/v2/search/image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ImageResponse

    companion object {
        const val TOKEN = "4976e78fcee524cea1b66690bb1c4425"
    }
}