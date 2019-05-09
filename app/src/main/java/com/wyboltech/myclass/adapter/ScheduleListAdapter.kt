package com.wyboltech.myclass.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.OnInteractionScheduleByMenuListener
import com.wyboltech.myclass.entities.ScheduleEntity
import com.wyboltech.myclass.viewholder.ScheduleViewHolder


class ScheduleListAdapter(val scheduleList: List<ScheduleEntity>,val listener: OnInteractionScheduleByMenuListener, val dayOfWeek: Int): RecyclerView.Adapter<ScheduleViewHolder>(){
    private val TYPEHEADER = 0
    private val TYPEROW = 1

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]

        when (getItemViewType(position)) {
            TYPEHEADER -> holder.bindDateDescription(schedule, dayOfWeek)
            TYPEROW -> holder.bindData(schedule)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var convertView: View? = View(context)
        when(viewType){
            TYPEHEADER -> {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_header_text_date_description, parent ,false)
                return ScheduleViewHolder(convertView,context, listener)
            }
            TYPEROW -> {
                convertView = inflater.inflate(R.layout.row_schedule_item_list, parent, false)
                return ScheduleViewHolder(convertView,context, listener)
            }
        }
        return ScheduleViewHolder(convertView!!,context, listener)
    }



    override fun getItemCount(): Int {
        return scheduleList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0)
            TYPEHEADER
        else
            TYPEROW
    }

}