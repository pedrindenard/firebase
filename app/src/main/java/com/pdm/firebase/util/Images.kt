package com.pdm.firebase.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.pdm.firebase.BuildConfig
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.gender.GenreDataImage

fun AppCompatImageView.loadImage(itemView: View, thumbnail: String?, size: String) {

    val image = BuildConfig.BASE_IMAGE_URL + size + thumbnail

    Glide.with(itemView)
        .load(image)
        .error(R.drawable.placeholder_movie)
        .placeholder(R.drawable.placeholder_movie)
        .into(this)
}

fun List<Gender>.addImages(param: String) {
    when (param) {
        "Movie" -> {
            GenreDataImage.genresImageByMovie.let {
                this.forEachIndexed { index, gender ->
                    if (index < it.size) {
                        gender.image = it[index]
                    }
                }
            }
        }
        "Tv" -> {
            GenreDataImage.genresImageByTv.let {
                this.forEachIndexed { index, gender ->
                    if (index < it.size) {
                        gender.image = it[index]
                    }
                }
            }
        }
    }
}