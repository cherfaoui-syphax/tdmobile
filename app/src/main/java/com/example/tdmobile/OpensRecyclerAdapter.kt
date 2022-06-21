package com.example.tdmobile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.OpensLayoutBinding

class OpensRecyclerAdapter(val context: Context, var data:List<Triple<String,String,String>>): RecyclerView.Adapter<OpensRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OpensLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            dayView.text = data[position].first
            fromView.text = data[position].second
            toView.text = data[position].third
        }
    }

    override fun getItemCount() = data.size
    class ViewHolder(val binding: OpensLayoutBinding): RecyclerView.ViewHolder(binding.root)
}