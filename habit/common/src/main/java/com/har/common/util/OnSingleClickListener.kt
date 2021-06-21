package com.har.common.util

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

class OnSingleClickListener(
        private val onClickListener: View.OnClickListener,
        private val interval: Long = 500
): View.OnClickListener {
    private var canClick= AtomicBoolean(true)

    override fun onClick(view: View?) {
        if (canClick.getAndSet(false)) {
            view?.run {
                postDelayed({
                    canClick.set(true)
                }, interval)
                onClickListener.onClick(view)
            }
        }
    }
}