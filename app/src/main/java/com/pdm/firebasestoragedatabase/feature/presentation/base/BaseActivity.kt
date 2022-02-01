package com.pdm.firebasestoragedatabase.feature.presentation.base

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun attachBaseContext(newBase: Context?) {
        applyOverrideConfiguration(
            Configuration(
                newBase?.resources?.configuration
            ).apply {
                fontScale = newBase?.getFontScale()!!
                handlerDpi()
            }
        )

        super.attachBaseContext(
            newBase
        )
    }

    private fun Context.getFontScale(): Float {
        val fontScale = this.resources.configuration.fontScale
        return when {
            fontScale > 1.2F -> {
                1.2F
            }
            fontScale < 0.8F -> {
                0.8F
            }
            else -> {
                fontScale
            }
        }
    }

    private fun Configuration.handlerDpi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
        }
    }
}