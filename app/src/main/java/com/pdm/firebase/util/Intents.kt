package com.pdm.firebase.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.presentation.activity.ErrorActivity
import com.pdm.firebase.feature.presentation.activity.VideoActivity

fun Activity.startPhone(url: String) {
    val i = Intent(Intent.ACTION_DIAL, Uri.parse(url))
    startActivity(i)
}

fun Activity.startEmail(url: String) {
    val i = Intent(Intent.ACTION_SENDTO)
    i.data = Uri.parse(url)
    startActivity(i)
}

private fun Activity.startWhatsApp(pack: String, url: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.setPackage(pack)
    i.data = Uri.parse(url)
    startActivity(i)
}

inline fun <reified T : Any> Context.launchActivity(extra: String? = null) {
    val intent = Intent(this, T::class.java)
    extra ?.let { intent.putExtra("Navigation", it) }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Activity.selectWhatsAppOrPlayStore(url: String) {
    when {
        whatsAppVerify(WHATSAPP_COMMON) -> startWhatsApp(WHATSAPP_COMMON, url)
        whatsAppVerify(WHATSAPP_BUSINESS) -> startWhatsApp(WHATSAPP_BUSINESS, url)
        else -> try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(INTENT_PLAY_STORE_APP)))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(INTENT_PLAY_STORE_WEB)))
        }
    }
}

private fun Activity.whatsAppVerify(url: String): Boolean {
    return try {
        val pm = packageManager
        pm?.getPackageInfo(url, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

fun FragmentActivity.onBackPressedListener(
    view: LifecycleOwner,
    onClickListenerEvent: (() -> Unit?)? = null
) {
    this.onBackPressedDispatcher.addCallback(view,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (onClickListenerEvent != null) {
                    onClickListenerEvent()
                }
            }
        }
    )
}

fun Activity.startVideoActivity(key: String) {
    startActivity(Intent(this, VideoActivity::class.java).apply {
        putExtra("video", key)
    })
}

fun Activity.startErrorActivity() {
    val intent = Intent(this, ErrorActivity::class.java)
    startActivity(intent)
}

fun Activity.initVideo(it: VideoResponse, action: () -> Unit) {
    it.results.takeIf { it.isNotEmpty() }?.run {
        forEach {
            if (it.type == "Trailer" && it.official) {
                startVideoActivity(
                    key = it.key
                )
                return@run
            }
        }
        forEach {
            if (it.type == "Trailer") {
                startVideoActivity(
                    key = it.key
                )
                return@run
            }
        }
        startVideoActivity(
            key = first().key
        )
    } ?: run { action() }
}