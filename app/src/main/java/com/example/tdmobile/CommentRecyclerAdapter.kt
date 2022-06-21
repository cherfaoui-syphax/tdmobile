package com.example.tdmobile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tdmobile.databinding.CommentLayoutBinding
import com.example.tdmobile.databinding.ParkingLayoutBinding
import com.example.tdmobile.entity.commentEntity

class CommentRecyclerAdapter(val context: Context, var data:List<commentEntity>): RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }



    override fun getItemCount() = data.size
    class ViewHolder(val binding: CommentLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            userName.text = data[position].userName
            comment.text = data[position].comment_text
            ratingBar.rating = data[position].rating.toFloat()
        }
    }
}
