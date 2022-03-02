package com.pdm.firebase.feature.presentation.fragment.upcoming.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.movie.Movie
import com.pdm.firebase.feature.presentation.fragment.upcoming.adapter.UpComingAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_MEDIUM_IMAGE
import com.pdm.firebase.util.loadImage
import java.util.*

class UpComingAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: MutableList<Movie> = mutableListOf()
    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_upcoming,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.title
        holder.date.text = mutableList.releaseDate
        holder.rating.rating = mutableList.averageVote
        holder.votes.text = mutableList.countVote.toString()
        holder.description.text = mutableList.overview
        holder.handleLanguage(mutableList)
        holder.handlerImage(mutableList)
    }

    override fun getItemCount(): Int = mutableList.size

    override fun getItemId(position: Int): Long {
        return mutableList[position].id.toLong()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: AppCompatImageView = itemView.findViewById(R.id.bannerUpComing)
        private var play: AppCompatImageView = itemView.findViewById(R.id.imagePlayYoutube)
        private var details: AppCompatButton = itemView.findViewById(R.id.imageViewDetail)
        private var language: AppCompatTextView = itemView.findViewById(R.id.language)
        var date: AppCompatTextView = itemView.findViewById(R.id.textDateMovie)
        var name: AppCompatTextView = itemView.findViewById(R.id.textNameMovie)
        var description: AppCompatTextView = itemView.findViewById(R.id.textDesMovie)
        var rating: RatingBar = itemView.findViewById(R.id.textTypeMovie)
        var votes: AppCompatTextView = itemView.findViewById(R.id.textVotes)

        init { details.setOnClickListener(this); play.setOnClickListener(this) }

        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.imageViewDetail -> {
                    mClickListener.onDetailsClickListener(
                        movie = mutableList[absoluteAdapterPosition]
                    )
                }
                R.id.imagePlayYoutube -> {
                    mClickListener.onPlayClickListener(
                        movie = mutableList[absoluteAdapterPosition]
                    )
                }
            }
        }

        fun handlerImage(it: Movie) {
            image.loadImage(
                thumbnail = it.imgBackground,
                itemView = itemView,
                size = TMDB_MEDIUM_IMAGE
            )
        }

        fun handleLanguage(it: Movie) {
            val originalLanguage = Locale(it.originalLanguage).displayLanguage
            language.text = originalLanguage.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: MutableList<Movie>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onDetailsClickListener(movie: Movie)
        fun onPlayClickListener(movie: Movie)
    }
}