package com.singhastudios.momento.data

import kotlinx.serialization.Serializable

@Serializable
data class CollectibleItem(
    val title: String,
    val genre: String,
    val desc: String,
    val seller: String,
    val thumb: String,
    val img: String,
    val price: Int,
    val location: String
)