package com.pdm.firebase.feature.presentation.fragment.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.presentation.fragment.search.adapter.SearchAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class SearchAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: MutableList<Search> = mutableListOf()
    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_search,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.imageGradient.visibility = View.GONE
        holder.handlerImage(mutableList)
    }

    override fun getItemId(position: Int): Long {
        return mutableList[position].id.toLong()
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var imageMovie: ShapeableImageView = itemView.findViewById(R.id.searchImage)
        var imageGradient: ShapeableImageView = itemView.findViewById(R.id.searchImageGradient)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                search = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: Search) {
            imageMovie.loadImage(
                thumbnail = it.posterPath ?: it.profilePath ?: "",
                itemView = itemView,
                size = TMDB_SMALL_IMAGE
            )
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
        fun onItemClickListener(search: Search)
    }
}