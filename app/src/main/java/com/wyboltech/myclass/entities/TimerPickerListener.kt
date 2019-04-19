package com.wyboltech.myclass.entities

import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import com.wyboltech.myclass.R
import java.util.*

class TimerPickerListener(val context: Context, val listener: TimePickerDialog.OnTimeSetListener):  View.OnClickListener{

    private val c: Calendar = Calendar.getInstance()
    private var hour = c.get(Calendar.HOUR_OF_DAY)
    private var minute = c.get(Calendar.MINUTE)

    override fun onClick(view: View) {
        val id = view.id
        val timePicker: TimePickerDialog
        if(id == R.id.img_btn_initTime){
            timePicker = TimePickerDialog(context, listener, hour, minute, true)
            timePicker.show()
        } else if (id == R.id.img_btn_finTime){
            timePicker = TimePickerDialog(context, listener, hour, minute, true)
            timePicker.show()
        }
    }
}