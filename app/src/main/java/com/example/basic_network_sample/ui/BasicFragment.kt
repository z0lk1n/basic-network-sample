package com.example.basic_network_sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basic_network_sample.App
import com.example.basic_network_sample.R
import com.example.basic_network_sample.databinding.FragmentBasicBinding
import com.example.basic_network_sample.network.models.ObjectResponse
import com.example.basic_network_sample.network.models.PhotoResponse
import com.example.basic_network_sample.utils.hide
import com.example.basic_network_sample.utils.network_state_manager.NetworkStateManager
import com.example.basic_network_sample.utils.network_state_manager.NetworkStateManagerImpl
import com.example.basic_network_sample.utils.show
import com.example.basic_network_sample.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasicFragment : Fragment(), NetworkStateManager.OnNetworkStateChangeListener {

  private var _binding: FragmentBasicBinding? = null

  // This property is only valid between onCreateView and onDestroyView.
  private val binding get() = _binding!!
  private var networkStateManager: NetworkStateManager? = null
  private val photoAdapter = PhotoAdapter()
  private var hasData: Boolean = false

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentBasicBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
    requestData()
  }

  private fun init() {
    networkStateManager = context?.let { NetworkStateManagerImpl(it) }
    networkStateManager?.register(this)

    binding.rvPhotos.run {
      layoutManager = LinearLayoutManager(context)
      adapter = photoAdapter
      setHasFixedSize(true)
    }
  }

  private fun requestData() {
    if (hasData) return

    showProgressBar(isShow = true)

    App.instance.apiService.getPhotos().enqueue(object : Callback<ObjectResponse> {
      override fun onResponse(call: Call<ObjectResponse>, response: Response<ObjectResponse>) {
        processRequestResult(isSuccess = true)
        val photos: List<PhotoResponse> = response.body()?.photos ?: emptyList()
        photoAdapter.addPhotos(photos)
      }

      override fun onFailure(call: Call<ObjectResponse>, t: Throwable) {
        processRequestResult(isSuccess = false)
      }
    })
  }

  private fun showProgressBar(isShow: Boolean) {
    activity?.runOnUiThread {
      if (isShow) binding.progressBar.show() else binding.progressBar.hide()
    }
  }

  private fun processRequestResult(isSuccess: Boolean) {
    showProgressBar(isShow = false)

    if (isSuccess) {
      hasData = true
      toast(R.string.success)
      binding.tvStub.hide()
      binding.rvPhotos.show()
    } else {
      toast(R.string.error)
      binding.rvPhotos.hide()
      binding.tvStub.show()
    }
  }

  override fun onNetworkStateChanged(isConnected: Boolean) {
    if (isConnected) requestData()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    networkStateManager?.unregister()
    _binding = null
  }
}