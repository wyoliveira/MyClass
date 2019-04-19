package com.wyboltech.myclass.entities

import android.view.MenuItem

interface OnInteractionScheduleByMenuListener: MenuItem.OnMenuItemClickListener {
    fun onEditClickItemMenu(scheduleId: Int)
    fun onDeleteClickItemMenu(scheduleId: Int)

    fun getOnMenuItemClickListener(): MenuItem.OnMenuItemClickListener {
        return this
    }
}