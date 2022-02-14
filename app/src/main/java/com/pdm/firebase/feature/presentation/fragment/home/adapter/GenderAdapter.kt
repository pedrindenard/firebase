package com.pdm.firebase.feature.presentation.fragment.home.adapter

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
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.presentation.fragment.home.adapter.GenderAdapter.ViewHolder

class GenderAdapter(private var mutableList: List<Gender>) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var mClickListener: ClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context.also {
                    mContext = it
                }
            ).inflate(
                R.layout.item_menu,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mutableList = mutableList[position]
        holder.gender.text = mutableList.name
        holder.handlerSelect(mutableList)
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var gender: AppCompatTextView = itemView.findViewById(R.id.menuName)

        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View?) {
            Event.adapterClick {
                mutableList.forEach { it.isSelect = false }
                mutableList[absoluteAdapterPosition].isSelect = true
                mClickListener.onItemClickListener(gender = mutableList[absoluteAdapterPosition])
                updateAdapter(mutableList = mutableList)
            }
        }

        fun handlerSelect(it: Gender) {
            when (it.isSelect) {
                true -> {
                    gender.setTextColor(
                        ContextCompat.getColor(
                            mContext, R.color.yellow_dark
                        )
                    )
                    gender.setBackgroundResource(
                        R.drawable.background_menu_movie
                    )
                }
                else -> {
                    gender.setTextColor(
                        ContextCompat.getColor(
                            mContext, R.color.white
                        )
                    )
                    gender.setBackgroundResource(
                        0
                    )
                }
            }
        }
    }

    @SuppressLint(value = ["NotifyDataSetChanged"])
    fun updateAdapter(mutableList: List<Gender>) {
        this.mutableList = mutableList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onItemClickListener(gender: Gender)
    }
}