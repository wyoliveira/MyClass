package com.wyboltech.myclass.views

import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.business.RoomBusiness
import com.wyboltech.myclass.business.ScheduleBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.RoomEntity
import com.wyboltech.myclass.entities.ScheduleEntity
import com.wyboltech.myclass.entities.TimerPickerListener
import com.wyboltech.myclass.util.SecurityPreferences
import com.wyboltech.myclass.util.ValidationException
import kotlinx.android.synthetic.main.activity_schedule_new_form.*

class ScheduleNewFormActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mRoomBusiness: RoomBusiness
    private lateinit var mScheduleBusiness: ScheduleBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mSharedPreferences: SharedPreferences
    private var mListRoomsEntity: MutableList<RoomEntity> = mutableListOf()
    private var mListRoomsId: MutableList<Int> = mutableListOf()
    private var mDayOfWeekCode = 0
    private var mScheduleId: Int = 0
    private var mListOfButtons = listOf<TextView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_new_form)

        mRoomBusiness = RoomBusiness(this)
        mScheduleBusiness = ScheduleBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mListOfButtons = listOf(bt_radio_segunda, bt_radio_terca, bt_radio_quarta,
                bt_radio_quinta, bt_radio_sexta, bt_radio_sabado)


        loadRooms()
        loadDataFromActivity()
        setListeners()
    }


    override fun onClick(view: View) {
        val id = view.id
        val listOfIdDayOfWeek = listOf(R.id.bt_radio_segunda,
                R.id.bt_radio_terca, R.id.bt_radio_quarta,
                R.id.bt_radio_quinta, R.id.bt_radio_sexta,
                R.id.bt_radio_sabado)

        if (id in listOfIdDayOfWeek) {
            handleDayOfWeek(id)
        } else if (id == R.id.btn_save_schedule_form) {
            handleSave()
        } else if (id == R.id.btn_cancel_schedule_form) {
            finish()
        }

    }

    private fun getTimeAddictedWithPreferenceTime(hour: Int, minute: Int): String {
        var mMin = minute
        var mHour = hour
        mMin += mSharedPreferences.getString(MyClassConstants.SETTINGS_KEY.LIST_DURATION_SCHEDULE, "40").toInt()

        return if (mMin >= 60) {
            while(mMin>=60){
                mMin-= 60
                mHour+=1
            }
            String.format("%02d:%02d", mHour, mMin)
        } else {
            String.format("%02d:%02d", mHour, mMin)
        }
    }

    private fun getOnTimeSetListener(id: Int): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, minute: Int ->
            if (Build.VERSION.SDK_INT >= 23) {
                if (id == R.id.img_btn_initTime) {
                    val initTimeString = String.format("%02d:%02d", timePicker.hour, timePicker.minute)
                    val finTimeString = getTimeAddictedWithPreferenceTime(timePicker.hour, timePicker.minute)
                    edit_select_initial_time.text = initTimeString
                    edit_select_final_time.text = finTimeString
                } else if (id == R.id.img_btn_finTime) {
                    val finTimeString = String.format("%02d:%02d", timePicker.hour, timePicker.minute)
                    edit_select_final_time.text = finTimeString
                }
                return@OnTimeSetListener
            } else {
                if (id == R.id.img_btn_initTime) {
                    val initTimeString = String.format("%02d:%02d", timePicker.currentHour, timePicker.currentMinute)
                    val finTimeString = getTimeAddictedWithPreferenceTime(timePicker.currentHour, timePicker.currentMinute)
                    edit_select_initial_time.text = initTimeString
                    edit_select_final_time.text = finTimeString
                } else if (id == R.id.img_btn_finTime) {
                    edit_select_final_time.text = String.format("%02d:%02d", timePicker.currentHour, timePicker.currentMinute)
                }
                return@OnTimeSetListener
            }
        }
    }

    private fun handleDayOfWeek(id: Int) {
        when (id) {
            R.id.bt_radio_segunda -> {
                changeColor(id, mListOfButtons)
            }
            R.id.bt_radio_terca -> {
                changeColor(id, mListOfButtons)
            }
            R.id.bt_radio_quarta -> {
                changeColor(id, mListOfButtons)
            }
            R.id.bt_radio_quinta -> {
                changeColor(id, mListOfButtons)
            }
            R.id.bt_radio_sexta -> {
                changeColor(id, mListOfButtons)
            }
            R.id.bt_radio_sabado -> {
                changeColor(id, mListOfButtons)
            }
        }
    }

    private fun changeColor(pId: Int, list: List<TextView>) {
        for (button in list) {
            if (button.id == pId) {
                if (Build.VERSION.SDK_INT >= 21) {
                    button.background = getDrawable(R.drawable.round_backgroud_shape)
                    button.setTextColor(resources.getColor(R.color.colorWhite))
                    mDayOfWeekCode = mListOfButtons.indexOf(button) + 1
                } else {
                    button.typeface = Typeface.DEFAULT_BOLD
                }

            } else {
                if (Build.VERSION.SDK_INT >= 21) {
                    button.background = getDrawable(R.drawable.round_background_week_day_unselected)
                    button.setTextColor(resources.getColor(R.color.colorBlackToGray))
                } else {
                    button.typeface = Typeface.DEFAULT
                }
            }
        }
    }

    private fun getIdOfButton(index: Int): Int = mListOfButtons[index - 1].id

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            mScheduleId = bundle.getInt(MyClassConstants.BUNDLE.SCHEDULEID)

            val schedule = mScheduleBusiness.get(mScheduleId)
            if (schedule != null) {
                edit_subject.setText(schedule.subject)
                edit_teacher.setText(schedule.teacher)
                spinnerRomm.setSelection(getIndex(schedule.roomId))
                edit_select_initial_time.text = schedule.initialDate
                edit_select_final_time.text = schedule.finalDate
                changeColor(getIdOfButton(schedule.dayOfWeek), mListOfButtons)
            }
        }
    }


    private fun handleSave() {
        try {
            val subject = edit_subject.text.toString()
            val teacher = edit_teacher.text.toString()
            val dayOfWeek = mDayOfWeekCode
            val userId = mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID).toInt()
            val room = mListRoomsId[spinnerRomm.selectedItemPosition]
            val initialTime = edit_select_initial_time.text.toString()
            val finalTime = edit_select_final_time.text.toString()

            val scheduleEntity = ScheduleEntity(mScheduleId, userId, subject, teacher, dayOfWeek, room, initialTime, finalTime)
            if (mScheduleId == 0) {
                mScheduleBusiness.insert(scheduleEntity)
                Toast.makeText(this, getString(R.string.horario_incluido_sucesso), Toast.LENGTH_LONG).show()
            } else {
                mScheduleBusiness.update(scheduleEntity)
                Toast.makeText(this, getString(R.string.horario_alterado_sucesso), Toast.LENGTH_SHORT).show()
            }
            finish()
        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun getIndex(id: Int): Int {
        var index = 0
        for (i in 0..mListRoomsEntity.size) {
            if (mListRoomsEntity[i].id == id) {
                index = i
                break
            }
        }
        return index
    }

    private fun loadRooms() {
        mListRoomsEntity = mRoomBusiness.getList()
        val lstRooms = mListRoomsEntity.map { it.description }
        mListRoomsId = mListRoomsEntity.map { it.id }.toMutableList()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstRooms)
        spinnerRomm.adapter = adapter
    }

    private fun setListeners() {
        btn_save_schedule_form.setOnClickListener(this)
        btn_cancel_schedule_form.setOnClickListener(this)
        bt_radio_segunda.setOnClickListener(this)
        bt_radio_terca.setOnClickListener(this)
        bt_radio_quarta.setOnClickListener(this)
        bt_radio_quinta.setOnClickListener(this)
        bt_radio_sexta.setOnClickListener(this)
        bt_radio_sabado.setOnClickListener(this)
        img_btn_initTime.setOnClickListener(TimerPickerListener(this, getOnTimeSetListener(img_btn_initTime.id)))
        img_btn_finTime.setOnClickListener(TimerPickerListener(this, getOnTimeSetListener(img_btn_finTime.id)))
    }
}
