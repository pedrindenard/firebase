package com.pdm.firebase.feature.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ClickableViewPager : ViewPager {

    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(context: Context?) : super(context!!) {
        setup()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        setup()
    }

    private fun setup() {
        val tapGestureDetector = GestureDetector(context, TapGestureListener())
        setOnTouchListener { v, event ->
            tapGestureDetector.onTouchEvent(event)
            v.performClick()
            false
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private inner class TapGestureListener : SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            mOnItemClickListener.takeIf {
                it != null
            } ?.let {
                mOnItemClickListener!!.onItemClick(
                    position = currentItem
                )
            }
            return true
        }
    }
}