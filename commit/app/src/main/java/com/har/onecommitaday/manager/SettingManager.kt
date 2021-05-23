package com.har.onecommitaday.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.har.onecommitaday.R

object SettingManager {

    fun getKeepGithubNickname(context: Context?): Boolean {
        context?.let {
            return getSharedPreference(it).getBoolean(it.getString(R.string.pref_key_keep_nickname), true)
        } ?: return true
    }


    fun getGithubNickname(context: Context?): String {
        context?.let {
            return getSharedPreference(it).getString(it.getString(R.string.pref_key_github_nickname), "") ?: ""
        } ?: return ""
    }

    fun setGithubNickname(context: Context?, nickname: String) {
        context?.let{
            getSharedPreference(it).edit().putString(it.getString(R.string.pref_key_github_nickname), nickname).apply()
        }
    }

    fun getApperanceType(context: Context?): String {
        context?.let {
            return getSharedPreference(it).getString(context.getString(R.string.pref_key_appearance_type), "jandi") ?: "jandi"
        } ?: return "jandi"
    }

    fun getNoFlowerColumn(context: Context?): Int {
        context?.let {
            return getSharedPreference(it).getInt(it.getString(R.string.pref_key_flower_column), 7)
        } ?: return 7
    }

    fun setNoFlowerColumn(context: Context?, noFlowerColumn: Int) {
        context?.let{
            getSharedPreference(it).edit().putInt(it.getString(R.string.pref_key_flower_column), noFlowerColumn).apply()
        }
    }

    private fun getSharedPreference(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

}