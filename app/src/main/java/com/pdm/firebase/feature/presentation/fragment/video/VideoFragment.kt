package com.pdm.firebase.feature.presentation.fragment.video

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.NonNull
import com.pdm.firebase.databinding.FragmentVideoBinding
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.util.ARGS
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener

class VideoFragment : BaseFragment() {

    private var youtube: YouTubePlayer? = null
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private var videoId: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            videoId = it.getSerializable(ARGS) as String
            handlerFlags(
                viewLifecycle = "onStart"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVideo()
    }

    private fun initVideo() {
        binding.youtubePlayer.enterFullScreen()
        binding.youtubePlayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(
                    videoId = videoId!!,
                    startSeconds = 0F
                )
                youtube = youTubePlayer
            }
        })

        binding.youtubePlayer.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                handlerFlags(viewLifecycle = "onStart")
            }

            override fun onYouTubePlayerExitFullScreen() {
                handlerFlags(viewLifecycle = "onFinish")
            }
        })
    }

    @SuppressLint(value = ["SourceLockedOrientationActivity"])
    private fun handlerFlags(viewLifecycle: String) {
        activity?.let {
            when (viewLifecycle) {
                "onStart" -> {
                    it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    it.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                }
                "onFinish" -> {
                    it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    it.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        youtube?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.youtubePlayer.release()
    }

    override fun onDetach() {
        super.onDetach()
        handlerFlags(
            viewLifecycle = "onFinish"
        )
    }
}