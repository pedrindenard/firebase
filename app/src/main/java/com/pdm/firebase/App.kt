package com.pdm.firebase

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.messaging.FirebaseMessaging
import com.pdm.firebase.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        startFirebase()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(
                listOf(
                    dataSourceModule,
                    repositoryModule,
                    userCasesModule,
                    viewModelModule,
                    dataModule
                )
            )
        }
    }

    private fun startFirebase() {
        FirebaseApp.initializeApp(applicationContext)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i("Token", "Error generate token ${task.exception!!.message.toString()}")
                return@OnCompleteListener
            }

            Log.i("Token", "Success generate token ${task.result}")
        })


        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            when (BuildConfig.BUILD_TYPE) {
                "release" -> {
                    //SafetyNetAppCheckProviderFactory.getInstance()
                    DebugAppCheckProviderFactory.getInstance()
                }
                else -> {
                    DebugAppCheckProviderFactory.getInstance()
                }
            }
        )
    }
}