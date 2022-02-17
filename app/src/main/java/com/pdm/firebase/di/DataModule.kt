package com.pdm.firebase.di

import com.pdm.firebase.App
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.database.ConnectDb
import com.pdm.firebase.feature.data.retrofit.ClientRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModule {
    val get = module {
        single { App() }
        single { CacheImpl(context = androidContext()) }
        single { ConnectDb.getInstance(context = androidContext()) }
        single { ClientRetrofit.create() }
    }
}