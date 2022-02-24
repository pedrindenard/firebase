package com.pdm.firebase.feature.presentation.fragment.review.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.review.Review
import com.pdm.firebase.feature.presentation.fragment.review.adapter.ReviewsAdapter.ViewHolder

class ReviewsAdapter(private val maxLineContent: Int? = null) : RecyclerView.Adapter<ViewHolder>() {

    private var mutableList: MutableList<Review> = mutableListOf()
    private lateinit var mClickListener: ClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also { mContext = it }
            ).inflate(
                R.layout.item_review,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.author
        holder.rating.rating = mutableList.authorDetails.rating ?: 0F
        holder.content.text = mutableList.content
        holder.handlerDates(mutableList)
        holder.handlerImage(mutableList)
        holder.handlerContent()
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var profile: ShapeableImageView = itemView.findViewById(R.id.profileAuthor)
        private var updatedAt: AppCompatTextView = itemView.findViewById(R.id.updatedAt)
        private var createdAt: AppCompatTextView = itemView.findViewById(R.id.createdAt)
        var name: AppCompatTextView = itemView.findViewById(R.id.nameAuthor)
        var rating: RatingBar = itemView.findViewById(R.id.ratingAuthor)
        var content: AppCompatTextView = itemView.findViewById(R.id.contentAuthor)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                review = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: Review) {
            Glide.with(itemView)
                .load(it.authorDetails.avatar?.substring(startIndex = 1))
                .error(R.drawable.placeholder_profile)
                .placeholder(R.drawable.placeholder_profile)
                .into(profile)
        }

        fun handlerDates(it: Review) {
            updatedAt.text = String.format(
                format = mContext.getString(R.string.review_updated),
                args = arrayOf(it.updatedAt.substringBefore(delimiter = "T"))
            )
            createdAt.text = String.format(
                format = mContext.getString(R.string.review_created),
                args = arrayOf(it.createdAt.substringBefore(delimiter = "T"))
            )
        }

        fun handlerContent() {
            content.apply {
                maxLineContent.takeIf { it != null }?.let {
                    ellipsize = TextUtils.TruncateAt.END
                    maxLines = it
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: MutableList<Review>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(review: Review)
    }
}