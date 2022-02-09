package com.pdm.firebase.feature.presentation.activity.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pdm.firebase.R

class IntroAdapter(private val context: Context, private val slides: ArrayList<Int>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater.inflate(
            when (slides[position]) {
                1 -> {
                    R.layout.item_slider_1
                }
                2 -> {
                    R.layout.item_slider_2
                }
                3 -> {
                    R.layout.item_slider_3
                }
                4 -> {
                    R.layout.item_slider_4
                }
                else -> {
                    R.layout.item_slider_5
                }
            }, container, false
        )

        container.addView(view)

        return view
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 1f
    }

    override fun destroyItem(arg0: View, arg1: Int, arg2: Any) {
        (arg0 as ViewPager).removeView(arg2 as View)
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1 as View
    }

    override fun saveState(): Parcelable? {
        return null
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return slides.size
    }
}