package com.wyboltech.myclass.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.wyboltech.myclass.constants.DataBaseConstants
import com.wyboltech.myclass.entities.TeacherEntity

class TeacherRepository private constructor(context: Context) {

    private val mScheduleDataBaseHelper: ScheduleDataBaseHelper = ScheduleDataBaseHelper(context)

    companion object {

        private var INSTANCE: TeacherRepository? = null
        fun getInstance(context: Context): TeacherRepository {
            if (INSTANCE == null) {
                INSTANCE = TeacherRepository(context)
            }
            return INSTANCE as TeacherRepository
        }
    }

    fun getList(pUserId: Int): MutableList<TeacherEntity> {

        val list = mutableListOf<TeacherEntity>()

        try {
            val cursor: Cursor

            val db = mScheduleDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.TEACHER.TABLE_NAME} " +
                    "WHERE ${DataBaseConstants.TEACHER.COLUMNS.USERID} = $pUserId", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val teacherId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.ID))
                    val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.USERID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.NAME))
                    val email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.EMAIL))

                    list.add(TeacherEntity(teacherId, userId, name, email))

                }
            }
            cursor.close()
            list.sortBy { it.name }

        } catch (e: Exception) {
            return list

        }
        return list
    }

    fun get(pId: Int): TeacherEntity? {
        var teacherEntity: TeacherEntity? = null

        try {
            val cursor: Cursor
            val db = mScheduleDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.TEACHER.COLUMNS.ID,
                    DataBaseConstants.TEACHER.COLUMNS.USERID,
                    DataBaseConstants.TEACHER.COLUMNS.NAME,
                    DataBaseConstants.TEACHER.COLUMNS.EMAIL)
            val selection = "${DataBaseConstants.TEACHER.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val selectionArgs = arrayOf(pId.toString())

            cursor = db.query(DataBaseConstants.TEACHER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                val teacherId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.NAME))
                val email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.EMAIL))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TEACHER.COLUMNS.USERID))
                teacherEntity = TeacherEntity(teacherId, userId, name, email)
            }
            cursor.close()

        } catch (e: java.lang.Exception) {
            return teacherEntity
        }
        return teacherEntity
    }

    fun insert(pTeacher: TeacherEntity) {

        try {
            val db = mScheduleDataBaseHelper.writableDatabase
            val insertValues = ContentValues()

            insertValues.put(DataBaseConstants.TEACHER.COLUMNS.USERID, pTeacher.userId)
            insertValues.put(DataBaseConstants.TEACHER.COLUMNS.NAME, pTeacher.name)
            insertValues.put(DataBaseConstants.TEACHER.COLUMNS.EMAIL, pTeacher.email)

            db.insert(DataBaseConstants.TEACHER.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }

    }

    fun update(pTeacher: TeacherEntity) {

        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val updateValues = ContentValues()

            updateValues.put(DataBaseConstants.TEACHER.COLUMNS.ID, pTeacher.id)
            updateValues.put(DataBaseConstants.TEACHER.COLUMNS.USERID, pTeacher.userId)
            updateValues.put(DataBaseConstants.TEACHER.COLUMNS.NAME, pTeacher.name)
            updateValues.put(DataBaseConstants.TEACHER.COLUMNS.EMAIL, pTeacher.email)

            val selection = "${DataBaseConstants.TEACHER.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val selectionArgs = arrayOf(pTeacher.id.toString())

            db.update(DataBaseConstants.TEACHER.TABLE_NAME, updateValues, selection, selectionArgs)

        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(pId: Int) {

        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val where = "${DataBaseConstants.TEACHER.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val whereArgs = arrayOf(pId.toString())

            db.delete(DataBaseConstants.TEACHER.TABLE_NAME, where, whereArgs)

        } catch (e: Exception) {
            throw e
        }
    }
}