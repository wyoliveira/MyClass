package com.wyboltech.myclass.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.OnInteractionItemTeacher
import com.wyboltech.myclass.entities.TeacherEntity
import com.wyboltech.myclass.viewholder.TeacherViewHolder

class TeacherListAdapter (val teacherList: List<TeacherEntity>, val listener: OnInteractionItemTeacher) : RecyclerView.Adapter<TeacherViewHolder>() {
    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teacherList[position]
        holder.bindData(teacher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_teacher_item_list, parent, false)

        return TeacherViewHolder(view, listener, context)

    }

    override fun getItemCount(): Int {
        return teacherList.count()
    }



}
