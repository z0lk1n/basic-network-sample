package com.example.basic_network_sample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun View.hide() {
  this.visibility = View.GONE
}

fun View.show() {
  this.visibility = View.VISIBLE
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, resId, duration).show()
}

fun Fragment.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
  context?.toast(resId, duration)
}

@Suppress("DEPRECATION")
fun Context?.hasNetwork(): Boolean {
  if (this == null) return false

  val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    val activeNetwork = connMgr.activeNetwork ?: return false
    val networkCapabilities = connMgr.getNetworkCapabilities(activeNetwork) ?: return false

    return when {
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
      networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
      else -> false
    }
  } else {
    return connMgr.activeNetworkInfo?.isConnected == true
  }
}