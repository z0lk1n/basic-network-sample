package com.example.basic_network_sample.utils.network_state_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.basic_network_sample.utils.hasNetwork

class NetworkStateManagerImpl(private val context: Context) : NetworkStateManager {

  private var listener: NetworkStateManager.OnNetworkStateChangeListener? = null
  private var networkReceiver: NetworkReceiver? = null
  private var networkCallback: ConnectivityManager.NetworkCallback? = null

  override fun register(listener: NetworkStateManager.OnNetworkStateChangeListener) {
    this.listener = listener

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      initNetworkCallback()
    } else {
      initNetworkReceiver()
    }
  }

  @RequiresApi(Build.VERSION_CODES.N)
  private fun initNetworkCallback() {
    networkCallback = object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        listener?.onNetworkStateChanged(isConnected = true)
      }

      override fun onLost(network: Network) {
        listener?.onNetworkStateChanged(isConnected = false)
      }
    }

    networkCallback?.let { getConnectivityManager().registerDefaultNetworkCallback(it) }
  }

  private fun getConnectivityManager(): ConnectivityManager {
    return context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  @Suppress("DEPRECATION")
  private fun initNetworkReceiver() {
    networkReceiver = NetworkReceiver()
    context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
  }

  override fun unregister() {
    listener = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      networkCallback?.let { getConnectivityManager().unregisterNetworkCallback(it) }
    } else {
      context.unregisterReceiver(networkReceiver)
    }
  }

  inner class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
      listener?.onNetworkStateChanged(context.hasNetwork())
    }
  }
}