package com.example.basic_network_sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basic_network_sample.R
import com.example.basic_network_sample.databinding.ItemPhotoBinding
import com.example.basic_network_sample.network.models.PhotoResponse

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

  private var photos: List<PhotoResponse> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemPhotoBinding.inflate(inflater, parent, false)
    return PhotoViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
    holder.bind(photos[position])
  }

  override fun getItemCount() = photos.size

  fun addPhotos(photos: List<PhotoResponse>) {
    this.photos = photos
    notifyDataSetChanged()
  }

  class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: PhotoResponse) = with(itemView) {
      Glide.with(context)
        .load(photo.source.photoUrl)
        .placeholder(R.drawable.ic_stub_image)
        .error(R.drawable.ic_broken_image)
        .into(binding.ivPhoto)

      binding.tvName.text = photo.photographer
    }
  }
}