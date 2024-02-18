package dev.asifddlks.friendshipclinic

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import dev.asifddlks.bhaibhaiclinicApp.utils.extensions.showToast
import dev.asifddlks.friendshipclinic.network.NetworkConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

//
// Created by Asif Ahmed on 17/10/23.
//
class FriendshipClinicApplication : Application() {

    init {
        instance = this
    }

    companion object {
        var instance: FriendshipClinicApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        networkConnectivityObserver()
    }

    private fun prepareDebugOrReleaseBuild() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {

        }
    }

    private fun networkConnectivityObserver() {
        CoroutineScope(Dispatchers.IO).launch {
            NetworkConnectivityObserver(applicationContext).observe().collectLatest {
                Handler(Looper.getMainLooper()).post {
                    showToast("Connection: $it")
                }
            }
        }
    }
}