package com.example.basic_network_sample.network.utils

import com.example.basic_network_sample.BuildConfig
import com.example.basic_network_sample.network.utils.NetworkConstants.APPLICATION_JSON_TYPE
import com.example.basic_network_sample.network.utils.NetworkConstants.AUTH_HEADER
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun Retrofit.Builder.setClient() = apply {
  val okHttpClient = OkHttpClient.Builder()
    .addHeaderInterceptor()
    .addHttpLoggingInterceptor()
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

  this.client(okHttpClient)
}

private fun OkHttpClient.Builder.addHeaderInterceptor() = apply {
  val interceptor = Interceptor { chain ->
    val request = chain.request()
      .newBuilder()
      .addHeader(AUTH_HEADER, BuildConfig.PEXELS_API_KEY)
      .build()

    chain.proceed(request)
  }

  this.addInterceptor(interceptor)
}

private fun OkHttpClient.Builder.addHttpLoggingInterceptor() = apply {
  if (BuildConfig.DEBUG) {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    this.addNetworkInterceptor(interceptor)
  }
}

@Suppress("EXPERIMENTAL_API_USAGE")
fun Retrofit.Builder.addJsonConverter() = apply {
  val json = Json { ignoreUnknownKeys = true }
  val contentType = APPLICATION_JSON_TYPE.toMediaType()
  this.addConverterFactory(json.asConverterFactory(contentType))
}