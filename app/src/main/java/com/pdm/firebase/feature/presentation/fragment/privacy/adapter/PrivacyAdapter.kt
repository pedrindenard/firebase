package com.pdm.firebase.feature.presentation.fragment.privacy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.privacy.Paragraph
import com.pdm.firebase.feature.presentation.fragment.privacy.adapter.PrivacyAdapter.ViewHolder

class PrivacyAdapter(private val paragraph: List<Paragraph>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_paragraph,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = paragraph[position].title
    }

    override fun getItemCount(): Int = paragraph.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var title: TextView = itemView.findViewById(R.id.titleParagraph)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            mClickListener.onItemClickListener(
                paragraph = paragraph[absoluteAdapterPosition]
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(paragraph: Paragraph)
    }
}