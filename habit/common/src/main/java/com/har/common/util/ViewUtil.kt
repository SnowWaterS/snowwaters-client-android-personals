package com.har.habitforyou.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.har.common.util.OnSingleClickListener

@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {
    isVisible = value
}

@BindingAdapter("android:invisibleIf")
fun View.setInvisibleIf(value: Boolean) {
    isInvisible = value
}

@BindingAdapter("android:goneIf")
fun View.setGoneIf(value: Boolean) {
    isGone = value
}

@BindingAdapter("android:html")
fun TextView.setHtml(value: String?) {
    value?.let {
        text = it.parseAsHtml()
    }
}

@BindingAdapter("android:textRes")
fun TextView.setTextRes(resId: Int) {
    if (resId > 0) {
        setText(resId)
    }
}

@BindingAdapter("android:srcRes")
fun ImageView.setResourceId(resId: Int) {
    if (resId > 0) {
        setImageResource(resId)
    }
}

@BindingAdapter("android:onSingleClick")
fun View.setOnSingleClickListener(listener: View.OnClickListener?) {
    setOnClickListener(listener?.let {
        OnSingleClickListener(it)
    })
}
