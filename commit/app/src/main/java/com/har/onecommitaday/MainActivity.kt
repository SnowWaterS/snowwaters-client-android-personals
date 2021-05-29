package com.har.onecommitaday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivity()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    private fun initActivity() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        when (sharedPreferences.getString(getString(R.string.pref_key_text_font), "system")) {
            "yeonji" ->  setTheme(R.style.Theme_OneCommitAday_Yeonji)
            "inklipquid" ->  setTheme(R.style.Theme_OneCommitAday_Inklipquid)
            "funnystory" ->  setTheme(R.style.Theme_OneCommitAday_Funnystory)
            "cookierun" ->  setTheme(R.style.Theme_OneCommitAday_CookieRun)
            else ->  setTheme(R.style.Theme_OneCommitAday_System)
        }

        when (sharedPreferences.getString(getString(R.string.pref_key_color_appearance), "system")) {
            "bright" ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}