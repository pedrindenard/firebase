package com.pdm.firebase.feature.presentation.activity

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.pdm.firebase.BuildConfig
import com.pdm.firebase.R

class VideoActivity : YouTubeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)
        val ytKey = intent.getStringExtra("video")

        ytPlayer.initialize(BuildConfig.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                value: Boolean
            ) {
                player?.loadVideo(ytKey)
                player?.play()
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {

            }
        })
    }
}