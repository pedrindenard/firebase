package com.pdm.firebase.feature.presentation.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.people.People
import com.pdm.firebase.feature.presentation.fragment.home.adapter.ActorAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class ActorAdapter(private val mutableList: MutableList<People>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_actors,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.name
        holder.picture.loadImage(
            thumbnail = mutableList.imgProfile,
            itemView = holder.itemView,
            size = TMDB_SMALL_IMAGE
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var picture: AppCompatImageView = itemView.findViewById(R.id.actorPicture)
        var name: AppCompatTextView = itemView.findViewById(R.id.actorName)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                people = mutableList[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(people: People)
    }
}