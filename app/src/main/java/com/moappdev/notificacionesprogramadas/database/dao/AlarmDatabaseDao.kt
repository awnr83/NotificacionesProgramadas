package com.moappdev.notificacionesprogramadas.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moappdev.notificacionesprogramadas.model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDatabaseDao {

    @Insert
    fun insertAlarm(alarm: Alarm):Long

    @Query("select * from alarm order by created asc")
    fun allAlarm():LiveData<List<Alarm>>

    @Delete
    fun deleteAlarm(alarm: Alarm)

    @Update
    fun updateAlarm(alarm: Alarm)
}