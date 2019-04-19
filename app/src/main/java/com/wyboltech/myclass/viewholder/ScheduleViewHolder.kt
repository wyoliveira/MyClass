package com.wyboltech.myclass.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.ScheduleEntity
import com.wyboltech.myclass.repository.RoomCacheConstants
import android.view.*
import com.wyboltech.myclass.entities.OnInteractionScheduleByMenuListener


class ScheduleViewHolder(itemView: View, val context: Context, val onClickedItemListener: OnInteractionScheduleByMenuListener): RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener{


    private val mTextSubject: TextView = itemView.findViewById(R.id.subject_name)
    private val mTextTeacher: TextView = itemView.findViewById(R.id.teacher_name)
    private val mTextRoom: TextView = itemView.findViewById(R.id.room_name)
    private val mTextInitialDate: TextView = itemView.findViewById(R.id.time_initial)
    private val mTextFinalDate: TextView = itemView.findViewById(R.id.time_final)
    private val mItemScheduleTable: RelativeLayout = itemView.findViewById(R.id.table_schedule)
    private var idSchedule: Int = 0

    fun bindData(schedule: ScheduleEntity) {
        idSchedule = schedule.id
        mTextSubject.text = schedule.subject
        mTextTeacher.text = schedule.teacher
        mTextRoom.text = RoomCacheConstants.getRoomDescription(schedule.roomId)
        mTextInitialDate.text = schedule.initialDate
        mTextFinalDate.text = schedule.finalDate

        mItemScheduleTable.setOnCreateContextMenuListener(this)


    }

    override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        val edit = menu.add(Menu.NONE, R.id.menu_item_edit_schedule, 1, "Edit")
        val delete = menu.add(Menu.NONE, R.id.menu_item_delete, 2, "Delete")

        edit.setOnMenuItemClickListener {
            onClickedItemListener.onEditClickItemMenu(idSchedule)
            return@setOnMenuItemClickListener true
        }
        delete.setOnMenuItemClickListener{
            onClickedItemListener.onDeleteClickItemMenu(idSchedule)
            return@setOnMenuItemClickListener true
        }
    }

}