package com.moappdev.notificacionesprogramadas.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.moappdev.notificacionesprogramadas.repository.AlarmRepository

class ReprogramAlarmService: LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val repo= AlarmRepository(application)
        repo.alarms.observe(this, Observer { alarms->
            alarms.forEach {
                if(it.started)
                    it.programAlarm(applicationContext)
            }
        })
        return START_STICKY
    }
}