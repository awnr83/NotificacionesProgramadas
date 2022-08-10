package com.moappdev.notificacionesprogramadas.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.moappdev.notificacionesprogramadas.service.AlarmService
import com.moappdev.notificacionesprogramadas.service.ReprogramAlarmService
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.FRIDAY
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.MONDAY
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.REPEAT
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.SATURDAY
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.SUNDAY
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.THURSDAY
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.TITLE
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.TUESDAY
import com.moappdev.notificacionesprogramadas.util.Constant.Companion.WEDNESDAY
import java.util.*

class AlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent?.action)){
            reprogramAlarmService(context)
        }else{
            if (!intent.getBooleanExtra(REPEAT, false)){
                startAlarmService(context, intent)
            }
            if (alarmNow(intent)) {
                startAlarmService(context, intent)
            }
        }
    }

    private fun reprogramAlarmService(context: Context) {
        val intenet= Intent(context,ReprogramAlarmService::class.java)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intenet)
        else
            context.startService(intenet)
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService= Intent(context, AlarmService::class.java)
        val title= intent.getStringExtra(TITLE)
        intentService.putExtra(TITLE,title)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intentService)
        else
            context.startService(intentService)
    }

    private fun alarmNow(intent: Intent):Boolean{
        val calendar= Calendar.getInstance()
        calendar.timeInMillis= System.currentTimeMillis()
        return when(calendar.get(Calendar.DAY_OF_WEEK)){
            Calendar.MONDAY -> intent.getBooleanExtra(MONDAY,false)
            Calendar.TUESDAY -> intent.getBooleanExtra(TUESDAY,false)
            Calendar.WEDNESDAY -> intent.getBooleanExtra(WEDNESDAY,false)
            Calendar.THURSDAY -> intent.getBooleanExtra(THURSDAY,false)
            Calendar.FRIDAY -> intent.getBooleanExtra(FRIDAY,false)
            Calendar.SATURDAY -> intent.getBooleanExtra(SATURDAY,false)
            Calendar.SUNDAY -> intent.getBooleanExtra(SUNDAY,false)
            else -> false
        }
    }
}