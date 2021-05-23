package com.har.onecommitaday.widgets

import android.view.View
import android.view.animation.Animation

class AlphaAnimationListener(val view: View): Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {
        view.visibility = View.VISIBLE
    }

    override fun onAnimationEnd(animation: Animation?) {
        view.visibility = View.GONE
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }

}