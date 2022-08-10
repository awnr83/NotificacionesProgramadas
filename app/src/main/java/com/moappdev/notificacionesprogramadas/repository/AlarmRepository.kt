package com.moappdev.notificacionesprogramadas.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moappdev.notificacionesprogramadas.database.AlarmDataBase
import com.moappdev.notificacionesprogramadas.database.dao.AlarmDatabaseDao
import com.moappdev.notificacionesprogramadas.model.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

class AlarmRepository(application: Application) {

    private val db: AlarmDataBase = AlarmDataBase.getInstance(application)

    private var alarmDao: AlarmDatabaseDao = db.alarmDatabaseDao
    val alarms:LiveData<List<Alarm>> = db.alarmDatabaseDao.allAlarm()

    suspend fun createAlarm(alarm: Alarm):Long?{
        return withContext(Dispatchers.IO){
            try {
                alarmDao.insertAlarm(alarm)
            }catch (e:Exception){
                Log.i("alfredo","error al insertar")
                null
            }
        }
    }

    suspend fun deleteAlarm(alarm: Alarm){
        withContext(Dispatchers.IO){
            alarmDao.deleteAlarm(alarm)
        }
    }

    suspend fun updateAlarm(alarm: Alarm){
        withContext(Dispatchers.IO){
            alarmDao.updateAlarm(alarm)
        }
    }
}