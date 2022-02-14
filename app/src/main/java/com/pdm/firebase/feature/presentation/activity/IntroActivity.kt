package com.pdm.firebase.feature.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.pdm.firebase.R
import com.pdm.firebase.databinding.ActivityIntroBinding
import com.pdm.firebase.feature.presentation.activity.adapter.IntroAdapter
import com.pdm.firebase.feature.presentation.view.ClickableViewPager
import java.util.*

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var adapter: IntroAdapter
    private lateinit var slides: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViewPageAdapter()

        setClickListener()
    }

    private fun initViewPageAdapter() {
        slides = arrayListOf(
            1, 2, 3, 4, 5
        )

        adapter = IntroAdapter(
            context = applicationContext,
            slides = slides
        )
    }

    private fun setClickListener() {
        val viewPageSlide = binding.viewPagerSlide
        val nextButton = binding.next

        viewPageSlide.apply {
            setOnItemClickListener(object : ClickableViewPager.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    if (position != 4) {
                        currentItem = position + 1
                    } else {
                        redirectToHome()
                    }
                }
            })
        }

        nextButton.apply {
            setOnClickListener {
                if (text == getString(R.string.done_login)) {
                    redirectToHome()
                }

                viewPageSlide.takeIf {
                    it.currentItem < slides.size
                }?.let {
                    viewPageSlide.currentItem += 1
                    return@setOnClickListener
                }
            }
        }

        viewPageSlide.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                /** Do nothing here**/
            }

            override fun onPageScrollStateChanged(state: Int) {
                /** Do nothing here**/
            }

            override fun onPageSelected(position: Int) {
                nextButton.text = getString(
                    if (position + 1 == slides.size) {
                        R.string.done_login
                    } else {
                        R.string.next_login
                    }
                )
            }
        })

        viewPageSlide.adapter = this.adapter
        viewPageSlide.offscreenPageLimit = 1
        viewPageSlide.clipToPadding = false
        viewPageSlide.pageMargin = 0

        binding.viewPagerIndicator.setupWithViewPager(viewPageSlide)
    }

    private fun redirectToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}