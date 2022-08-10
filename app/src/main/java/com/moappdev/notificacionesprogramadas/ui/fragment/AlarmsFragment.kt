package com.moappdev.notificacionesprogramadas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.moappdev.notificacionesprogramadas.R
import com.moappdev.notificacionesprogramadas.databinding.FragmentAlarmsBinding
import com.moappdev.notificacionesprogramadas.model.Alarm
import com.moappdev.notificacionesprogramadas.ui.adapter.AlarmAdapter
import com.moappdev.notificacionesprogramadas.ui.viewmodel.CreateAlarmViewModel


class AlarmsFragment : Fragment() {

    private var _binding:FragmentAlarmsBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewmodel: CreateAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAlarmsBinding.inflate(layoutInflater)
        viewmodel= ViewModelProvider(this).get(CreateAlarmViewModel::class.java)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.setOnClickListener {
            findNavController().navigate(AlarmsFragmentDirections.actionAlarmsFragmentToCreateAlarmFragment())
        }
        setupRv()
    }

    private fun setupRv() {
        val adapter=AlarmAdapter{ alarm->
            if(alarm.started){
                alarm.cancelAlarm(requireContext())
                viewmodel.update(alarm)
            }else{
                alarm.programAlarm(requireContext())
                viewmodel.update(alarm)
            }
        }

        binding.rvAlarms.adapter= adapter
        viewmodel.alarms.observe(viewLifecycleOwner,{
            it?.let{
                adapter.submitList(it)
            }
        })
    }
}