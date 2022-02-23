package com.pdm.firebase.feature.presentation.fragment.details.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.video.Video
import com.pdm.firebase.feature.presentation.fragment.details.movie.adapter.VideoAdapter.ViewHolder
import com.pdm.firebase.util.MAXIMUM_QUALITY_YOUTUBE
import com.pdm.firebase.util.loadThumbnail

class VideoAdapter(
    private val mutableList: List<Video>
) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_video,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.handlerVideo(mutableList)
        //holder.name.text = mutableList.name
        //holder.type.text = mutableList.type
        //holder.date.text = mutableList.publishedAt.substringBefore(delimiter = "T")
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var video: ShapeableImageView = itemView.findViewById(R.id.videoImage)
        var name: AppCompatTextView = itemView.findViewById(R.id.videoTitle)
        var date: AppCompatTextView = itemView.findViewById(R.id.videoDate)
        var type: AppCompatTextView = itemView.findViewById(R.id.videoType)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                video = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerVideo(it: Video) {
            video.loadThumbnail(
                quality = MAXIMUM_QUALITY_YOUTUBE,
                itemView = itemView,
                key = it.key,
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(video: Video)
    }
}