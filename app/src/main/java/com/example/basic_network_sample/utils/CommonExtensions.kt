package com.example.basic_network_sample.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes

fun View.hide() {
  this.visibility = View.GONE
}

fun View.show() {
  this.visibility = View.VISIBLE
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, resId, duration).show()
}