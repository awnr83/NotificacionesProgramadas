package com.moappdev.notificacionesprogramadas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moappdev.notificacionesprogramadas.database.dao.AlarmDatabaseDao
import com.moappdev.notificacionesprogramadas.model.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDataBase:RoomDatabase() {

    abstract val alarmDatabaseDao: AlarmDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: AlarmDataBase?=null

        fun getInstance(context: Context):AlarmDataBase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance=Room.databaseBuilder(
                        context.applicationContext,
                        AlarmDataBase::class.java,
                        "alarm_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}