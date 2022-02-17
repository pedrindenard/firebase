package com.pdm.firebase.feature.presentation.fragment.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pdm.firebase.R
import com.pdm.firebase.arquitecture.Event
import com.pdm.firebase.feature.domain.model.region.Region
import com.pdm.firebase.feature.presentation.fragment.search.adapter.RegionAdapter.ViewHolder
import com.pdm.firebase.util.dpToPx

class RegionAdapter(private var mutableList: List<Region>) : RecyclerView.Adapter<ViewHolder>(){

    private lateinit var mClickListener: ClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also { mContext = it }
            ).inflate(
                R.layout.item_filter,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.name.text = mutableList.initials
        holder.handlerSelect(mutableList)
        holder.handlerLayout()
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var name: AppCompatTextView = itemView.findViewById(R.id.menuFilter)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            Event.adapterClick {
                mutableList.forEach { it.isSelect = false }
                mutableList[absoluteAdapterPosition].isSelect = true
                mClickListener.onItemClickListener(region = mutableList[absoluteAdapterPosition])
                updateAdapter(mutableList = mutableList)
            }
        }

        fun handlerLayout() {
            name.width = mContext.dpToPx(dp = 40F)
        }

        fun handlerSelect(it: Region) {
            when (it.isSelect) {
                true -> {
                    name.setTextColor(
                        ContextCompat.getColor(
                            mContext, R.color.green
                        )
                    )
                }
                else -> {
                    name.setTextColor(
                        ContextCompat.getColor(
                            mContext, R.color.white
                        )
                    )
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: List<Region>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(region: Region)
    }
}