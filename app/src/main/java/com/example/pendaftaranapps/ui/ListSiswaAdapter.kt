package com.example.pendaftaranapps.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pendaftaranapps.data.response.DataItem
import com.example.pendaftaranapps.databinding.ItemSiswaBinding

class ListSiswaAdapter: ListAdapter<DataItem, ListSiswaAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemSiswaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: DataItem){
            binding.namaSiswa.text = dataItem.nama
            binding.sekolahAsal.text = dataItem.sekolahAsal
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DataItem>(){
            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}