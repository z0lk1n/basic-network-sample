package com.example.basic_network_sample.network

import com.example.basic_network_sample.network.models.ObjectResponse
import com.example.basic_network_sample.network.utils.NetworkConstants.BASE_URL
import com.example.basic_network_sample.network.utils.addJsonConverter
import com.example.basic_network_sample.network.utils.setClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("search")
  fun getPhotos(@Query("query") endpoint: String = "nature"): Call<ObjectResponse>

  companion object {

    fun create(): ApiService {
      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .setClient()
        .addJsonConverter()
        .build()
        .create(ApiService::class.java)
    }
  }
}