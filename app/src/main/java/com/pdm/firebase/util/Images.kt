package com.pdm.firebase.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.pdm.firebase.BuildConfig
import com.pdm.firebase.R
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.gender.GenreDataImage

fun AppCompatImageView.loadImage(itemView: View, thumbnail: String?, size: String, error: Int? = null) {

    val image = BuildConfig.TMDB_IMAGE_URL + size + thumbnail

    val placeHolder = error ?: R.drawable.placeholder_movie

    Glide.with(itemView)
        .load(image)
        .error(placeHolder)
        .placeholder(placeHolder)
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

fun ShapeableImageView.loadThumbnail(itemView: View, key: String, quality: String) {

    val url = BuildConfig.YOUTUBE_IMAGE_URL + key + quality

    Glide.with(itemView)
        .load(url)
        .error(R.drawable.placeholder_movie)
        .placeholder(R.drawable.placeholder_movie)
        .into(this)
}