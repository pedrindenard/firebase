package com.pdm.firebase.feature.presentation.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.tv.TvShow
import com.pdm.firebase.feature.presentation.fragment.home.adapter.TvAdapter.ViewHolder
import com.pdm.firebase.util.SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class TvAdapter(private val mutableList: MutableList<TvShow>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_best_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.title.text = mutableList.name
        holder.rate.text = mutableList.voteAverage.toString()
        holder.ratingBar.rating = mutableList.voteAverage
        holder.thumbnail.loadImage(
            thumbnail = mutableList.foreground,
            itemView = holder.itemView,
            size = SMALL_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var thumbnail: AppCompatImageView = itemView.findViewById(R.id.moviePicture)
        var title: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        var rate: AppCompatTextView = itemView.findViewById(R.id.movieRateNumber)
        var ratingBar: RatingBar = itemView.findViewById(R.id.movieRateStar)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                tvShow = mutableList[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(tvShow: TvShow)
    }
}
