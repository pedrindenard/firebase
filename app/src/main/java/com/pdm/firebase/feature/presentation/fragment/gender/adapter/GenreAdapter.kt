package com.pdm.firebase.feature.presentation.fragment.gender.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.presentation.fragment.gender.adapter.GenreAdapter.ViewHolder
import com.pdm.firebase.util.SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class GenreAdapter(private val mutableList: List<Gender>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_genres,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.name
        holder.thumbnail.loadImage(
            itemView = holder.itemView,
            thumbnail = mutableList.image ?: "",
            size = SMALL_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var name: AppCompatTextView = itemView.findViewById(R.id.genreName)
        var thumbnail: AppCompatImageView = itemView.findViewById(R.id.genreImage)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                gender = mutableList[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(gender: Gender)
    }
}