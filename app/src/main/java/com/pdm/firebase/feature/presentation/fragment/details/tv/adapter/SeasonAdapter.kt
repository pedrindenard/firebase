package com.pdm.firebase.feature.presentation.fragment.details.tv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.tv.details.Season
import com.pdm.firebase.feature.presentation.fragment.details.tv.adapter.SeasonAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class SeasonAdapter(
    private val mutableList: List<Season>
) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also { mContext = it }
            ).inflate(
                R.layout.item_season,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.name
        holder.overView.text = mutableList.overview
        holder.handlerSeason(mutableList)
        holder.handlerImage(mutableList)
        holder.handlerDate(mutableList)
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: ShapeableImageView = itemView.findViewById(R.id.seasonPicture)
        private var number: AppCompatTextView = itemView.findViewById(R.id.seasonNumber)
        private var date: AppCompatTextView = itemView.findViewById(R.id.seasonDate)
        var name: AppCompatTextView = itemView.findViewById(R.id.seasonName)
        var overView: AppCompatTextView = itemView.findViewById(R.id.seasonOverView)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                season = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: Season) {
            image.loadImage(
                thumbnail = it.posterPath,
                itemView = itemView,
                size = TMDB_SMALL_IMAGE
            )
        }

        fun handlerSeason(it: Season) {
            number.text = String.format(
                mContext.getString(R.string.episodes_number), it.episodeCount
            )
        }

        fun handlerDate(it: Season) {
            date.text = String.format(
                mContext.getString(R.string.release_on), it.airDate
            )

            date.visibility = if (it.airDate.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(season: Season)
    }
}