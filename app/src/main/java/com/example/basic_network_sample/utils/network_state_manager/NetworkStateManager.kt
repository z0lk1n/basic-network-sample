package com.example.basic_network_sample.utils.network_state_manager

interface NetworkStateManager {

  fun register(listener: OnNetworkStateChangeListener)

  fun unregister()

  interface OnNetworkStateChangeListener {

    fun onNetworkStateChanged(isConnected: Boolean)
  }
}