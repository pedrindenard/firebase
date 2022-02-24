package com.pdm.firebase.feature.presentation.fragment.details.people.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.tagged.Tagged
import com.pdm.firebase.feature.presentation.fragment.details.people.adapter.TaggedAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class TaggedAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: MutableList<Tagged> = mutableListOf()
    private lateinit var mClickListener: ClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also { mContext = it }
            ).inflate(
                R.layout.item_tagged,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.media.title ?: mutableList.media.name
        holder.date.text = mutableList.media.releaseDate ?: mutableList.media.firstAirDate
        holder.rate.rating = mutableList.media.voteAverage!!
        holder.description.text = mutableList.media.overview
        holder.handlerImage(mutableList)
        holder.handlerMedia(mutableList)
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: AppCompatImageView = itemView.findViewById(R.id.banner)
        private var type: AppCompatTextView = itemView.findViewById(R.id.type)
        var name: AppCompatTextView = itemView.findViewById(R.id.name)
        var date: AppCompatTextView = itemView.findViewById(R.id.date)
        var description: AppCompatTextView = itemView.findViewById(R.id.description)
        var rate: RatingBar = itemView.findViewById(R.id.rating)
        var details: AppCompatButton = itemView.findViewById(R.id.info)

        init { details.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                tagged = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: Tagged) {
            image.loadImage(
                thumbnail = it.media.posterPath,
                itemView = itemView,
                size = TMDB_SMALL_IMAGE
            )
        }

        fun handlerMedia(it: Tagged) {
            type.text = when (it.mediaType) {
                "movie" -> {
                    mContext.getString(R.string.media_type_movie)
                }
                else -> {
                    mContext.getString(R.string.media_type_tv)
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: MutableList<Tagged>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(tagged: Tagged)
    }
}