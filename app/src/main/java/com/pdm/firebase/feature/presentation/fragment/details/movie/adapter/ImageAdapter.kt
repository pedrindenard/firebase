package com.pdm.firebase.feature.presentation.fragment.details.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.image.Image
import com.pdm.firebase.feature.presentation.fragment.details.movie.adapter.ImageAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_LARGE_IMAGE
import com.pdm.firebase.util.loadImage

class ImageAdapter(private val mutableList: List<Image>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.image.loadImage(
            itemView = holder.itemView,
            thumbnail = mutableList.url,
            size = TMDB_LARGE_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var image: AppCompatImageView = itemView.findViewById(R.id.imageBanner)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                image = mutableList[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(image: Image)
    }
}