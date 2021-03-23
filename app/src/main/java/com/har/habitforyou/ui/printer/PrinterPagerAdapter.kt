package com.har.habitforyou.ui.printer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.har.habitforyou.R
import com.har.habitforyou.ui.printer.tab.extension.PrinterExtensionFragment
import com.har.habitforyou.ui.printer.tab.image.PrinterImageFragment
import com.har.habitforyou.ui.printer.tab.settings.PrinterSettingsFragment
import com.har.habitforyou.ui.printer.tab.text.PrinterTextFragment

class PrinterPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PrinterImageFragment()
            }
            1 -> {
                PrinterTextFragment()
            }
            2 -> {
                PrinterExtensionFragment()
            }
            else -> {
                PrinterSettingsFragment()
            }
        }
    }

    fun getTitleResId(position: Int): Int {
        return when (position) {
            0 -> {
                R.string.printer_title_image
            }
            1 -> {
                R.string.printer_title_text
            }
            2 -> {
                R.string.printer_title_extension
            }
            else -> {
                R.string.printer_title_settings
            }
        }
    }

    fun getIconResId(position: Int): Int {
        return when (position) {
            0 -> {
                R.drawable.ic_printer_image
            }
            1 -> {
                R.drawable.ic_priner_text
            }
            2 -> {
                R.drawable.ic_printer_extension
            }
            else -> {
                R.drawable.ic_printer_settings
            }
        }
    }

}