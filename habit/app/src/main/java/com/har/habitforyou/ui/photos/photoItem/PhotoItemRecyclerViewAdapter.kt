package com.har.habitforyou.ui.photos.photoItem

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.har.habitforyou.R
import com.har.habitforyou.databinding.ItemPhotoBinding

import com.har.habitforyou.ui.photos.dummy.DummyContent.DummyItem

class PhotoItemRecyclerViewAdapter(
    private val values: List<DummyItem>,
    private val columnCount: Int
) : RecyclerView.Adapter<PhotoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val params = binding.root.layoutParams
        params.height=parent.measuredWidth/columnCount
        binding.root.layoutParams=params

        return PhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size
}


class PhotoItemViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DummyItem) {
        binding.content.text = "${item.id}:${item.content}"
    }
}