package com.moappdev.notificacionesprogramadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moappdev.notificacionesprogramadas.databinding.ActivityRingBinding
import com.moappdev.notificacionesprogramadas.model.Alarm
import com.moappdev.notificacionesprogramadas.service.AlarmService
import java.util.*

class RingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSnooze.setOnClickListener {
            val calendar= Calendar.getInstance()
            calendar.timeInMillis= System.currentTimeMillis()
            calendar.add(Calendar.MINUTE,1)

            val alarm= Alarm(
                0,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                "Snooze",
                System.currentTimeMillis(),
                false, false,false,false,false,false,false,false,false)

            val intentService= Intent(applicationContext, AlarmService::class.java)
            alarm.programAlarm(applicationContext)
            applicationContext.stopService(intentService)
            finish()
        }

        binding.btnDismiss.setOnClickListener {
            val intentService= Intent(applicationContext,AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
    }
}