package com.example.basic_network_sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basic_network_sample.App
import com.example.basic_network_sample.R
import com.example.basic_network_sample.databinding.ActivityMainBinding
import com.example.basic_network_sample.network.models.ObjectResponse
import com.example.basic_network_sample.network.models.PhotoResponse
import com.example.basic_network_sample.utils.hide
import com.example.basic_network_sample.utils.show
import com.example.basic_network_sample.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val photoAdapter = PhotoAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.rvPhotos.run {
      layoutManager = LinearLayoutManager(context)
      adapter = photoAdapter
      setHasFixedSize(true)
    }

    binding.progressBar.show()
    App.instance.apiService.getPhotos().enqueue(object : Callback<ObjectResponse> {
      override fun onResponse(
        call: Call<ObjectResponse>,
        response: Response<ObjectResponse>
      ) {
        binding.progressBar.hide()
        toast(R.string.success)
        val photos: List<PhotoResponse> = response.body()?.photos ?: emptyList()
        photoAdapter.addPhotos(photos)
      }

      override fun onFailure(call: Call<ObjectResponse>, t: Throwable) {
        binding.progressBar.hide()
        toast(R.string.error)
      }
    })
  }
}