package com.ku_stacks.ku_ring.analytics

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class EventAnalytics(context: Context) {

    private val delegate by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    private fun logEvent(name: String, params: Bundle.() -> Unit){
        delegate.logEvent(name, Bundle().apply{ params() })
    }

    fun click(screenName: String, screenClass: String) {
        logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }
}