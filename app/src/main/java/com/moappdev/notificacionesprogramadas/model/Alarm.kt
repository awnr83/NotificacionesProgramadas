package com.moappdev.notificacionesprogramadas.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moappdev.notificacionesprogramadas.broadcastreceiver.AlarmBroadcastReceiver
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

@Entity(tableName = "alarm")
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    var alarmId:Int=0,
    var hour:Int,
    var minute:Int,
    var title:String,
    var created:Long,
    var started:Boolean,
    var repeat:Boolean,
    var monday:Boolean,
    var tuesday:Boolean,
    var wednesday:Boolean,
    var thursday:Boolean,
    var friday:Boolean,
    var saturday:Boolean,
    var sunday:Boolean
) {
    fun programAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent= Intent(context,AlarmBroadcastReceiver::class.java)
        intent.putExtra(TITLE,title)
        intent.putExtra(MONDAY,monday)
        intent.putExtra(TUESDAY,tuesday)
        intent.putExtra(WEDNESDAY,wednesday)
        intent.putExtra(THURSDAY,thursday)
        intent.putExtra(FRIDAY,friday)
        intent.putExtra(SATURDAY,saturday)
        intent.putExtra(SUNDAY,sunday)
        intent.putExtra(REPEAT,repeat)

        //ver alarmId
        val intentPending= PendingIntent.getBroadcast(context, alarmId, intent, 0)

        val calendar= Calendar.getInstance()
        calendar.timeInMillis=System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)

        if(calendar.timeInMillis <= System.currentTimeMillis()) //si la alarma ya paso se incrementa en un dia
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1)

        if(repeat){//si tildo alguno se repite
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,intentPending)
        }else{
            val day:Long =24 * 60 * 60 * 1000
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis, day, intentPending)
        }
        this.started= true
    }

    fun cancelAlarm(context: Context){
        val alarmManager: AlarmManager= context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)

        //ver alarmId
        val pendingIntent= PendingIntent.getBroadcast(context, alarmId, intent, 0)

        alarmManager.cancel(pendingIntent)
        this.started=false
        Log.i("alfredo","cancelado....")
    }
}