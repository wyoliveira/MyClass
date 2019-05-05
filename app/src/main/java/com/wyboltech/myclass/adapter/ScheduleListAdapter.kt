package com.wyboltech.myclass.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.OnInteractionScheduleByMenuListener
import com.wyboltech.myclass.entities.ScheduleEntity
import com.wyboltech.myclass.viewholder.ScheduleViewHolder


class ScheduleListAdapter(val list: List<ScheduleEntity>,val listener: OnInteractionScheduleByMenuListener, val dayOfWeek: Int): RecyclerView.Adapter<ScheduleViewHolder>(){
    private val TYPE_HEADER = 0
    private val TYPE_ROW = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var convertView: View? = View(context)
        when(viewType){
            TYPE_HEADER -> {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_header_text_date_description, parent ,false)
                return ScheduleViewHolder(convertView,context, listener)
            }
            TYPE_ROW -> {
                convertView = inflater.inflate(R.layout.row_schedule_item_list, parent, false)
                return ScheduleViewHolder(convertView,context, listener)
            }
        }
        return ScheduleViewHolder(convertView!!,context, listener)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = list[position]

        when (getItemViewType(position)) {
            TYPE_HEADER -> holder.bindDateDescription(schedule, dayOfWeek)
            TYPE_ROW -> holder.bindData(schedule)
        }

    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0)
            TYPE_HEADER
        else
            TYPE_ROW
    }

}