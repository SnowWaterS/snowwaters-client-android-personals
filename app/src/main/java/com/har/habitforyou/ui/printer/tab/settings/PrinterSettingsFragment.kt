package com.har.habitforyou.ui.printer.tab.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.har.habitforyou.R
import com.har.habitforyou.util.BluetoothUtil

class PrinterSettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.printer_preferences, rootKey)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String) {
        val pref = findPreference<Preference>(key)
        if (pref?.key == getString(R.string.pref_key_printer_connect_bluetooth_device_title)) {
            if ((pref as SwitchPreference).isChecked) {
                Log.i("PrinterSettingsFragment", "블루투스 연결")
                BluetoothUtil.instance?.scannedBluetoothDevices()
//                if (!scannedDevices.isNullOrEmpty()) {
//                    scannedDevices.forEach {
//                        Log.i("PrinterSettingsFragment", "기기 목록: $it")
//                    }
//                }
            } else {
                Log.i("PrinterSettingsFragment", "블루투스 연결 안 함")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}