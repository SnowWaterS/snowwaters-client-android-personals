package com.har.habittracker.domain.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.har.habittracker.util.AlarmHelper

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val alarmHelper = AlarmHelper()
        alarmHelper.createAlarm(context)
    }
}