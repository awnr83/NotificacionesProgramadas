package com.moappdev.notificacionesprogramadas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moappdev.notificacionesprogramadas.R
import com.moappdev.notificacionesprogramadas.databinding.ItemAlarmBinding
import com.moappdev.notificacionesprogramadas.model.Alarm

class AlarmAdapter(private val clickListener: (Alarm)->Unit):ListAdapter<Alarm,AlarmAdapter.ViewHolder>(AlarmDiffUtil()) {
    class AlarmDiffUtil:DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.alarmId==newItem.alarmId
        }

    }

    class ViewHolder(val binding: ItemAlarmBinding, val context: Context):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Alarm){
            binding.apply {
                itemAlarmTime.text=String.format("%02d:%02d",item.hour,item.minute)
                itemAlarmTitle.text= item.title
                itemAlarmRecurringDays.text= ""
                itemAlarmStarted.isChecked= item.started
                if(item.repeat){
                    itemAlarmRecurring.setImageResource(R.drawable.ic_repeat)
                }else{
                    itemAlarmRecurring.setImageResource(R.drawable.ic_no_repeat)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmAdapter.ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding= ItemAlarmBinding.inflate(layoutInflater,parent,false)
        val viewHolder =ViewHolder(binding,parent.context)

        viewHolder.binding.itemAlarmStarted.setOnClickListener {
            val pos= viewHolder.adapterPosition
            clickListener(getItem(pos))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AlarmAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
//class ClickListener(val clickListener: (item: Alarm)->Unit) {
//    fun onClick(item: Alarm)= clickListener(item)
//}