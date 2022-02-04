package com.pdm.firebase.feature.presentation.fragment.privacy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.privacy.Content
import com.pdm.firebase.feature.presentation.fragment.privacy.adapter.ContentAdapter.ViewHolder
import com.pdm.firebase.util.addViews
import com.pdm.firebase.util.collapse
import com.pdm.firebase.util.expand
import com.pdm.firebase.util.rotateView

class ContentAdapter(private val content: List<Content>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also { context = it }
            ).inflate(
                R.layout.item_content,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = content[position]
        holder.title.text = content.subTitle
        holder.handlerDescription(content)
    }

    override fun getItemCount(): Int = content.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var description: LinearLayoutCompat = itemView.findViewById(R.id.contentDescription)
        var expandArrow: AppCompatImageView = itemView.findViewById(R.id.expandArrow)
        var title: TextView = itemView.findViewById(R.id.subTitleContent)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            description.apply {
                expandArrow.rotateView(
                    if (isGone) {
                        expand(speed = 1); true
                    } else {
                        collapse(speed = 1); false
                    },
                    speed = 200, angleN = -90f, angleP = 90f
                )
            }
        }

        fun handlerDescription(content: Content) {
            description.addViews(
                description = content.description,
                requireContext = context
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(content: Content)
    }
}