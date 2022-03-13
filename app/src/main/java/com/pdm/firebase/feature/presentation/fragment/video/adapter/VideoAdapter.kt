package com.pdm.firebase.feature.presentation.fragment.video.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.presentation.fragment.video.adapter.VideoAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_MEDIUM_IMAGE
import com.pdm.firebase.util.loadImage
import com.pdm.firebase.util.margin
import java.util.*

class VideoAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: MutableList<Search> = mutableListOf()
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
        holder.date.text = mutableList.releaseDate ?: mutableList.firstAirDate
        holder.name.text = mutableList.title ?: mutableList.name
        holder.rate.text = mutableList.voteAverage.toString()
        holder.handleLanguage(mutableList)
        holder.handlerImage(mutableList)
        holder.handlerFirst()
    }

    override fun getItemCount(): Int = mutableList.size

    override fun getItemId(position: Int): Long {
        return mutableList[position].id.toLong()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: AppCompatImageView = itemView.findViewById(R.id.bannerUpComing)
        private var play: AppCompatImageView = itemView.findViewById(R.id.imagePlayYoutube)
        private var language: AppCompatTextView = itemView.findViewById(R.id.language)
        private var details: AppCompatButton = itemView.findViewById(R.id.imageViewDetail)
        var rate: AppCompatTextView = itemView.findViewById(R.id.rateNumber)
        var name: AppCompatTextView = itemView.findViewById(R.id.name)
        var date: AppCompatTextView = itemView.findViewById(R.id.date)

        init { details.setOnClickListener(this); play.setOnClickListener(this) }

        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.imageViewDetail -> {
                    mClickListener.onDetailsClickListener(
                        search = mutableList[absoluteAdapterPosition]
                    )
                }
                R.id.imagePlayYoutube -> {
                    mClickListener.onPlayClickListener(
                        search = mutableList[absoluteAdapterPosition]
                    )
                }
            }
        }

        fun handlerImage(it: Search) {
            image.loadImage(
                thumbnail = it.backdropPath,
                itemView = itemView,
                size = TMDB_MEDIUM_IMAGE
            )
        }

        fun handlerFirst() {
            rate.margin(
                top = when (absoluteAdapterPosition) {
                    0 -> {
                        32F
                    }
                    else -> {
                        8F
                    }
                }
            )
        }

        fun handleLanguage(it: Search) {
            val originalLanguage = Locale(it.originalLanguage!!).displayLanguage
            originalLanguage.replace(oldValue = "\u0020", newValue = "")
            language.text = originalLanguage.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: MutableList<Search>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onDetailsClickListener(search: Search)
        fun onPlayClickListener(search: Search)
    }
}