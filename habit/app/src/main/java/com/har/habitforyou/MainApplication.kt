package com.har.habitforyou

import android.app.Application
import com.har.habitforyou.util.ContextUtil

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // add context
        ContextUtil.appContext = applicationContext
    }
}