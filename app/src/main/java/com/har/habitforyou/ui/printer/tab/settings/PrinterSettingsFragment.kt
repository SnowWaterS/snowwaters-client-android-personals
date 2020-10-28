package com.har.habitforyou.ui.printer.tab.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.har.habitforyou.R

class PrinterSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}