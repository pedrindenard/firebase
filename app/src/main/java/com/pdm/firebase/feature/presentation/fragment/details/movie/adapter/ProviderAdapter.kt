package com.pdm.firebase.feature.presentation.fragment.details.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderFlatRate
import com.pdm.firebase.feature.presentation.fragment.details.movie.adapter.ProviderAdapter.ViewHolder
import com.pdm.firebase.util.TMDB_SMALL_IMAGE
import com.pdm.firebase.util.loadImage

class ProviderAdapter(
    private val mutableList: List<ProviderFlatRate>
) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_provider,
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
        private var image: AppCompatImageView = itemView.findViewById(R.id.provider)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                provider = mutableList[absoluteAdapterPosition]
            )
        }

        fun handlerImage(it: ProviderFlatRate) {
            image.loadImage(
                thumbnail = it.logo,
                itemView = itemView,
                size = TMDB_SMALL_IMAGE
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(provider: ProviderFlatRate)
    }
}