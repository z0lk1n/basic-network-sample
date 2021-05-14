package com.example.basic_network_sample.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectResponse(@SerialName("photos") val photos: List<PhotoResponse>)

@Serializable
data class PhotoResponse(
  @SerialName("id") val id: Int,
  @SerialName("photographer") val photographer: String,
  @SerialName("src") val source: SourceResponse
)

@Serializable
data class SourceResponse(@SerialName("landscape") val photoUrl: String)