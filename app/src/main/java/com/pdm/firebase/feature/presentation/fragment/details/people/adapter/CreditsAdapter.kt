package com.pdm.firebase.feature.presentation.fragment.details.people.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCredits
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.presentation.fragment.details.people.adapter.CreditsAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class CreditsAdapter(private val mutableList: MutableList<PeopleCredits>) : RecyclerView.Adapter<ViewHolder>() {

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
        holder.thumbnail.loadImage(
            thumbnail = mutableList.background,
            itemView = holder.itemView,
            size = TMDB_SMALL_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var thumbnail: AppCompatImageView = itemView.findViewById(R.id.imageMovie)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                credits = mutableList[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(credits: PeopleCredits)
    }
}