package com.wyboltech.myclass.business

import android.content.Context
import com.wyboltech.myclass.R
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.TeacherEntity
import com.wyboltech.myclass.repository.TeacherRepository
import com.wyboltech.myclass.util.SecurityPreferences
import com.wyboltech.myclass.util.ValidationException

class TeacherBusiness (val context: Context){
    private val mTeacherRepository: TeacherRepository = TeacherRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(pId: Int) = mTeacherRepository.get(pId)
    fun getList(): MutableList<TeacherEntity>{
        val userId =  mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID).toInt()
        return mTeacherRepository.getList(userId)
    }
    fun update(teacherEntity: TeacherEntity) = mTeacherRepository.update(teacherEntity)
    fun delete(id: Int) = mTeacherRepository.delete(id)
    fun insert(teacherEntity: TeacherEntity){

        try {
            if (teacherEntity.name.isBlank() && teacherEntity.email.isBlank()) {
                throw ValidationException(context.getString(R.string.preencha_campos))
            } else if (teacherEntity.name.isBlank()){
                throw ValidationException(context.getString(R.string.necessario_nome_professor))
            } else {
                mTeacherRepository.insert(teacherEntity)
                throw ValidationException(context.getString(R.string.professor_incluido))
            } 
        }catch (e: Exception){
            throw e
        }
    }

}