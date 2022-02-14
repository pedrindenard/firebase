package com.pdm.firebase.feature.presentation.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.presentation.fragment.home.adapter.BannerAdapter.ViewHolder
import com.pdm.firebase.util.LARGE_IMAGE
import com.pdm.firebase.util.loadImage

class BannerAdapter(private val mutableList: MutableList<Movie>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_banner,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.title.text = mutableList.title
        holder.thumbnail.loadImage(
            thumbnail = mutableList.imgBackground,
            itemView = holder.itemView,
            size = LARGE_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var thumbnail: AppCompatImageView = itemView.findViewById(R.id.bannerMovie)
        var title: AppCompatTextView = itemView.findViewById(R.id.titleMovie)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                movie = mutableList[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(movie: Movie)
    }
}