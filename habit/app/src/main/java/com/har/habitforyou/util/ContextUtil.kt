package com.har.habitforyou.util

import android.content.Context

class ContextUtil {

    companion object {
        var appContext: Context? = null
            set(context: Context?) {
                field = context?.applicationContext
            }
    }

}