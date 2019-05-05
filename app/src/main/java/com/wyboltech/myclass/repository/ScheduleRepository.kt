package com.wyboltech.myclass.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.wyboltech.myclass.constants.DataBaseConstants
import com.wyboltech.myclass.entities.ScheduleEntity
import java.lang.Exception

class ScheduleRepository private constructor(context: Context) {

    private var mScheduleDataBaseHelper: ScheduleDataBaseHelper = ScheduleDataBaseHelper(context)

    companion object {

        private var INSTANCE: ScheduleRepository? = null

        fun getInstance(context: Context): ScheduleRepository {
            if (INSTANCE == null) {
                INSTANCE = ScheduleRepository(context)
            }
            return INSTANCE as ScheduleRepository
        }
    }

    fun getList(pUserId: Int, pDayOfWeek: Int): MutableList<ScheduleEntity> {
        val list = mutableListOf<ScheduleEntity>()

        try {
            val cursor: Cursor

            val db = mScheduleDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.SCHEDULE.TABLE_NAME} " +
                    "WHERE ${DataBaseConstants.SCHEDULE.COLUMNS.USERID} = $pUserId " +
                    "AND ${DataBaseConstants.SCHEDULE.COLUMNS.DAYOFWEEK} = $pDayOfWeek", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val scheduleId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.ID))
                    val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.USERID))
                    val subjectName = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.SUBJECT))
                    val teacher = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.TEACHER))
                    val dayofweek = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.TEACHER))
                    val roomId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.ROOMID))
                    val initialDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.INITIAL_DATE))
                    val finalDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.FINAL_DATE))

                    list.add(ScheduleEntity(scheduleId, userId, subjectName, teacher, dayofweek, roomId, initialDate, finalDate))

                }
            }
            cursor.close()
            list.sortBy { it.initialDate }

        } catch (e: Exception) {
            return list

        }
        return list
    }
    //get, update, insert e delete

    fun get(pScheduleId: Int): ScheduleEntity? {
        var schedule: ScheduleEntity? = null

        try {
            val cursor: Cursor
            val db = mScheduleDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.SCHEDULE.COLUMNS.ID,
                    DataBaseConstants.SCHEDULE.COLUMNS.USERID,
                    DataBaseConstants.SCHEDULE.COLUMNS.SUBJECT,
                    DataBaseConstants.SCHEDULE.COLUMNS.TEACHER,
                    DataBaseConstants.SCHEDULE.COLUMNS.DAYOFWEEK,
                    DataBaseConstants.SCHEDULE.COLUMNS.ROOMID,
                    DataBaseConstants.SCHEDULE.COLUMNS.INITIAL_DATE,
                    DataBaseConstants.SCHEDULE.COLUMNS.FINAL_DATE)

            val selection = "${DataBaseConstants.SCHEDULE.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro recebendo o id como parametro

            val selectionArgs = arrayOf(pScheduleId.toString())

            cursor = db.query(DataBaseConstants.SCHEDULE.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val scheduleId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.USERID))
                val subjectName = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.SUBJECT))
                val teacher = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.TEACHER))
                val dayOfWeek = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.DAYOFWEEK))
                val roomId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.ROOMID))
                val initialDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.INITIAL_DATE))
                val finalDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SCHEDULE.COLUMNS.FINAL_DATE))

                schedule = ScheduleEntity(scheduleId, userId, subjectName, teacher, dayOfWeek, roomId, initialDate, finalDate)

            }
            cursor.close()

        } catch (e: Exception) {
            return schedule
        }

        return schedule
    }

    fun insert(pScheduleEntity: ScheduleEntity) {
        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.USERID, pScheduleEntity.userId)
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.SUBJECT, pScheduleEntity.subject)
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.TEACHER, pScheduleEntity.teacher)
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.DAYOFWEEK, pScheduleEntity.dayOfWeek)
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.ROOMID, pScheduleEntity.roomId)
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.INITIAL_DATE, pScheduleEntity.initialDate)
            insertValues.put(DataBaseConstants.SCHEDULE.COLUMNS.FINAL_DATE, pScheduleEntity.finalDate)

            db.insert(DataBaseConstants.SCHEDULE.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }

    }

    fun update(pScheduleEntity: ScheduleEntity) {

        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.ID, pScheduleEntity.id)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.USERID, pScheduleEntity.userId)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.SUBJECT, pScheduleEntity.subject)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.TEACHER, pScheduleEntity.teacher)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.DAYOFWEEK, pScheduleEntity.dayOfWeek)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.ROOMID, pScheduleEntity.roomId)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.INITIAL_DATE, pScheduleEntity.initialDate)
            updateValues.put(DataBaseConstants.SCHEDULE.COLUMNS.FINAL_DATE, pScheduleEntity.finalDate)

            val selection = "${DataBaseConstants.SCHEDULE.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val selectionArgs = arrayOf(pScheduleEntity.id.toString())

            db.update(DataBaseConstants.SCHEDULE.TABLE_NAME, updateValues, selection, selectionArgs)

        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(pScheduleId: Int) {
        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val where = "${DataBaseConstants.SCHEDULE.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val whereArgs = arrayOf(pScheduleId.toString())

            db.delete(DataBaseConstants.SCHEDULE.TABLE_NAME, where, whereArgs)

        } catch (e: Exception){
            throw e
        }
    }

}