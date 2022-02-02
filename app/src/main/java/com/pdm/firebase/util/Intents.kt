package com.pdm.firebase.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

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