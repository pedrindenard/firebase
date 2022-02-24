package com.pdm.firebase.feature.presentation.fragment.details.people.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.people.People
import com.pdm.firebase.feature.presentation.fragment.details.people.adapter.PeopleAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.formatMovie
import com.pdm.firebase.util.loadImage

class PeopleAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: MutableList<People> = mutableListOf()
    private lateinit var mClickListener: ClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also { mContext = it }
            ).inflate(
                R.layout.item_people,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.name
        holder.date.text = mutableList.knowByDepartment
        holder.rate.text = mutableList.popularity.toString()
        holder.handlerImage(mutableList)
        holder.handlerMedia(mutableList)
        holder.description.text = String.format(
            format = mContext.getString(R.string.know_for_media),
            args = arrayOf(mutableList.knowFor.formatMovie())
        )
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: AppCompatImageView = itemView.findViewById(R.id.banner)
        private var type: AppCompatTextView = itemView.findViewById(R.id.type)
        var name: AppCompatTextView = itemView.findViewById(R.id.name)
        var date: AppCompatTextView = itemView.findViewById(R.id.date)
        var description: AppCompatTextView = itemView.findViewById(R.id.description)
        var rate: AppCompatTextView = itemView.findViewById(R.id.rating)
        var details: AppCompatButton = itemView.findViewById(R.id.info)

        init { details.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                people = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: People) {
            image.loadImage(
                thumbnail = it.imgProfile,
                itemView = itemView,
                size = TMDB_SMALL_IMAGE
            )
        }

        fun handlerMedia(it: People) {
            type.text = when (it.gender) {
                1 -> {
                    mContext.getString(R.string.girl_radio)
                }
                2 -> {
                    mContext.getString(R.string.man_radio)
                }
                else -> {
                    mContext.getString(R.string.other_radio)
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: MutableList<People>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(people: People)
    }
}