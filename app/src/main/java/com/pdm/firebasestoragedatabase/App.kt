package com.pdm.firebasestoragedatabase

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.pdm.firebasestoragedatabase.di.dataSourceModule
import com.pdm.firebasestoragedatabase.di.repositoryModule
import com.pdm.firebasestoragedatabase.di.userCasesModule
import com.pdm.firebasestoragedatabase.di.viewModelModule
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
    }
}