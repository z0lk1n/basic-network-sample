package com.example.basic_network_sample

import android.app.Application
import com.example.basic_network_sample.network.ApiService

class App : Application() {

  init {
    instance = this
  }

  val apiService: ApiService by lazy { ApiService.create() }

  override fun onCreate() {
    super.onCreate()
  }

  companion object {
    lateinit var instance: App
      private set
  }
}