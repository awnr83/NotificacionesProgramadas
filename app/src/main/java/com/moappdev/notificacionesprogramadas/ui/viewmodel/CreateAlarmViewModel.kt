package com.moappdev.notificacionesprogramadas.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.moappdev.notificacionesprogramadas.model.Alarm
import com.moappdev.notificacionesprogramadas.repository.AlarmRepository
import kotlinx.coroutines.launch

class CreateAlarmViewModel(application: Application) : AndroidViewModel(application) {

    private val alarmRepository: AlarmRepository = AlarmRepository(application)

    private var _nro= MutableLiveData<Long?>()
    val nro: LiveData<Long?>
        get() = _nro

    val alarms= alarmRepository.alarms

    fun insert(alarm: Alarm){
        viewModelScope.launch {
            val x: Long? = alarmRepository.createAlarm(alarm)
            _nro.postValue(x)
        }
    }
    fun update(alarm: Alarm){
        viewModelScope.launch {
            alarmRepository.updateAlarm(alarm)
        }
    }

}
