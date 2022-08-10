package com.moappdev.notificacionesprogramadas.util

import android.os.Build
import android.widget.TimePicker

object TimePikerUtil {
    fun getHour(tp:TimePicker):Int{
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            tp.hour
        else
            tp.currentHour
    }
    fun getMinute(tp:TimePicker):Int{
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            tp.minute
        else
            tp.currentMinute
    }
}