package com.pdm.firebase.util

import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

fun View.rotateView(rotate: Boolean, speed: Long, angleN: Float, angleP: Float): Boolean {
    this.animate().setDuration(speed)
        .setListener(object : AnimatorListenerAdapter() {})
        .rotation(if (rotate) angleN else angleP)
    return rotate
}

fun View.expand(speed: Int) {
    this.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = this.measuredHeight
    this.layoutParams.height = 1
    this.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            this@expand.layoutParams.height =
                if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT
                else (targetHeight * interpolatedTime).toInt()
            this@expand.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = ((targetHeight / this.context.resources.displayMetrics.density).toInt().toLong() * speed)
    this.startAnimation(a)
}

fun View.collapse(speed: Int) {
    val initialHeight: Int = this.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                this@collapse.visibility = View.GONE
            } else {
                this@collapse.layoutParams.height =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                this@collapse.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = ((initialHeight / this.context.resources.displayMetrics.density).toInt().toLong() * speed)
    this.startAnimation(a)
}

fun RecyclerView.startIntroAnimation() {
    this.translationY = 0F
    this.alpha = 0F
    this.animate()
        .translationY(0F)
        .setDuration(500)
        .alpha(1F)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}