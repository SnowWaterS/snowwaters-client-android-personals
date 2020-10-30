package com.har.habitforyou.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.har.habitforyou.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)
    }

    override fun onStart() {
        super.onStart()

        initPerferences()
    }

    private fun initPerferences() {

    }
}