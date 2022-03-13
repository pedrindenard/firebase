package com.pdm.firebase.feature.presentation.fragment.details.tv.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.tv.TvShow
import com.pdm.firebase.feature.presentation.fragment.details.tv.adapter.SimilarAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class SimilarAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: List<TvShow> = mutableListOf()
    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.handlerImage(mutableList)
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: ShapeableImageView = itemView.findViewById(R.id.imageMovie)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                tv = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: TvShow) {
            image.loadImage(
                thumbnail = it.foreground,
                itemView = itemView,
                size = TMDB_SMALL_IMAGE
            )
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: MutableList<TvShow>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(tv: TvShow)
    }
}