package com.har.onecommitaday.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.har.onecommitaday.R

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        val keepNickname =
            findPreference<CheckBoxPreference>(getString(R.string.pref_key_keep_nickname))
        if (keepNickname?.isChecked == true) {
            keepNickname.summary = preferenceManager.sharedPreferences.getString(
                getString(R.string.pref_key_github_nickname),
                ""
            )
        }

        val appearanceType =
            findPreference<ListPreference>(getString(R.string.pref_key_appearance_type))
        val noFlowerColumn = findPreference<Preference>(getString(R.string.pref_key_flower_column))
        val flowerType =
            resources.getStringArray(R.array.type_values).firstOrNull { it.contains("flower") }
        if (appearanceType?.value == flowerType) {
            noFlowerColumn?.isEnabled = true
            val countColumn = preferenceManager.sharedPreferences.getInt(
                getString(R.string.pref_key_flower_column),
                7
            )
            noFlowerColumn?.summary = countColumn.toString()
        } else {
            noFlowerColumn?.isEnabled = false
            noFlowerColumn?.summary = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if (key == getString(R.string.pref_key_appearance_type)) {
            val value = sharedPreferences?.getString(key, "jandi") ?: "jandi"
            val noFlowerColumn =
                findPreference<Preference>(getString(R.string.pref_key_flower_column))
            val flowerType =
                resources.getStringArray(R.array.type_values).firstOrNull { it.contains("flower") }
            if (value == flowerType) {
                noFlowerColumn?.isEnabled = true
                val countColumn =
                    sharedPreferences?.getInt(getString(R.string.pref_key_flower_column), 7)
                noFlowerColumn?.summary = countColumn.toString()
            } else {
                noFlowerColumn?.isEnabled = false
                noFlowerColumn?.summary = null
            }
        }

        if (key == getString(R.string.pref_key_text_font)) {
            val value = sharedPreferences?.getString(key, "system") ?: "system"
            Log.i("valueTest", "선택한 값: $value")
        }

        if (key == getString(R.string.pref_key_color_appearance)) {
            val value = sharedPreferences?.getString(key, "system") ?: "system"
            Log.i("valueTest", "선택한 값: $value")
        }

        activity?.recreate()
    }
}