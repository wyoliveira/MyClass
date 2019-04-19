package com.wyboltech.myclass.business

import android.content.Context
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.ScheduleEntity
import com.wyboltech.myclass.repository.ScheduleRepository
import com.wyboltech.myclass.util.SecurityPreferences
import com.wyboltech.myclass.util.ValidationException
import java.lang.Exception

class ScheduleBusiness (context: Context) {

    private val mScheduleRepository: ScheduleRepository = ScheduleRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id: Int) = mScheduleRepository.get(id)

    fun getList(dayOfWeek: Int): MutableList<ScheduleEntity>{
        val userId = mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID).toInt()
        return mScheduleRepository.getList(userId, dayOfWeek)

    }

    fun insert(scheduleEntity: ScheduleEntity) {

        try {
            if( scheduleEntity.dayOfWeek == 0){
                throw ValidationException("Por favor, escolha o dia da semana")
            }

            mScheduleRepository.insert(scheduleEntity)

        } catch (e: Exception){
            throw e
        }
    }

    fun update(scheduleEntity: ScheduleEntity) = mScheduleRepository.update(scheduleEntity)

    fun delete(scheduleId: Int) = mScheduleRepository.delete(scheduleId)

}