package com.example.imagesearch.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: String
) : Parcelable

fun List<Document>.toModel(): List<ImageModel> =
    map { it.toModel() }

fun Document.toModel(): ImageModel =
    ImageModel(
        collection = collection,
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        width = width,
        height = height,
        displaySiteName = displaySiteName,
        docUrl = docUrl,
        datetime = datetime
    )