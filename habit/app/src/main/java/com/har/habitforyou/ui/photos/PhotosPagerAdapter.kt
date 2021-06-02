package com.har.habitforyou.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.har.habitforyou.R
import com.har.habitforyou.ui.photos.photoItem.PhotoListFragment
import com.har.habitforyou.util.ContextUtil


class PhotosPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoListFragment()
            else -> PhotoEditFragment()

        }
    }

    fun getView(position: Int): View {
        val view = LayoutInflater.from(ContextUtil.appContext).inflate(R.layout.tab_photo, null)
        val img = view.findViewById<ImageView>(R.id.iv_tab)
        img.setImageResource(getIconResId(position))
        val text = view.findViewById<TextView>(R.id.tv_tab)
        text.setText(getTitleResId(position))
        return view
    }

    fun getTitleResId(position: Int): Int {
        return when (position) {
            0 -> R.string.photos_title_photos
            else -> R.string.photos_title_edit
        }
    }

    fun getIconResId(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_photo_album
            else -> R.drawable.ic_photo_edit
        }
    }
}