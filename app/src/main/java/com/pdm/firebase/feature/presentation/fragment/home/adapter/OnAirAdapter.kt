package com.pdm.firebase.feature.presentation.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.tv.TvShow
import com.pdm.firebase.feature.presentation.fragment.home.adapter.OnAirAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_MEDIUM_IMAGE
import com.pdm.firebase.util.loadImage

class OnAirAdapter(private val mutableList: MutableList<TvShow>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_showcase,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.title.text = mutableList.name
        holder.date.text = mutableList.releaseDate
        holder.thumbnail.loadImage(
            thumbnail = mutableList.background,
            itemView = holder.itemView,
            size = TMDB_MEDIUM_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var thumbnail: AppCompatImageView = itemView.findViewById(R.id.showcaseImage)
        var title: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        var date: AppCompatTextView = itemView.findViewById(R.id.movieDate)

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