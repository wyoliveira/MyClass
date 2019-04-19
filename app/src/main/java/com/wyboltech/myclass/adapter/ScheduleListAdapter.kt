package com.wyboltech.myclass.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.OnInteractionScheduleByMenuListener
import com.wyboltech.myclass.entities.ScheduleEntity
import com.wyboltech.myclass.viewholder.ScheduleViewHolder

class ScheduleListAdapter(val list: List<ScheduleEntity>,val listener: OnInteractionScheduleByMenuListener): RecyclerView.Adapter<ScheduleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_schedule_item_list, parent, false)
        return ScheduleViewHolder(view, context, listener)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = list[position]
        holder.bindData(schedule)

    }

    override fun getItemCount(): Int {
        return list.count()
    }

}