package com.moappdev.notificacionesprogramadas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.moappdev.notificacionesprogramadas.databinding.FragmentCreateAlarmBinding
import com.moappdev.notificacionesprogramadas.model.Alarm
import com.moappdev.notificacionesprogramadas.ui.viewmodel.CreateAlarmViewModel
import com.moappdev.notificacionesprogramadas.util.TimePikerUtil
import java.util.*


class CreateAlarmFragment : Fragment() {

    private var _binding: FragmentCreateAlarmBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewmodel:CreateAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCreateAlarmBinding.inflate(layoutInflater)

        binding.btnList.setOnClickListener {
            findNavController().navigate(CreateAlarmFragmentDirections.actionCreateAlarmFragmentToAlarmsFragment())
        }
        setup()
        programAlarm()

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun setup(){
        binding.tpTime.setIs24HourView(true)
        //viewmodel = ViewModelProviders.of(this)[CreateAlarmViewModel::class.java]
        viewmodel= ViewModelProvider(this).get(CreateAlarmViewModel::class.java)

    }
    private fun programAlarm(){
        binding.apply {
            btnCreate.setOnClickListener {
                val created= System.currentTimeMillis()
                val repeat= cbMonday.isChecked or cbTuesday.isChecked or cbWednesday.isChecked or cbThursday.isChecked or cbFriday.isChecked or cbSaturday.isChecked or cbSunday.isChecked
                val alarm= Alarm(0,TimePikerUtil.getHour(tpTime),TimePikerUtil.getMinute(tpTime),tieTitle.text.toString(), created,true, repeat, cbMonday.isChecked,cbTuesday.isChecked,cbWednesday.isChecked,cbThursday.isChecked,cbFriday.isChecked,cbSaturday.isChecked,cbSunday.isChecked)

                viewmodel.insert(alarm)
                viewmodel.nro.observe(viewLifecycleOwner, Observer {
                    it?.let {id->
                        alarm.alarmId= id.toInt()
                        alarm.programAlarm(requireContext())
                    }
                })
                tieTitle.setText("")
                cbSunday.isChecked=false
                cbMonday.isChecked=false
                cbTuesday.isChecked=false
                cbWednesday.isChecked=false
                cbThursday.isChecked=false
                cbFriday.isChecked=false
                cbSaturday.isChecked=false
            }
        }
    }
}
