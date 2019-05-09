package com.wyboltech.myclass.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.wyboltech.myclass.constants.DataBaseConstants
import com.wyboltech.myclass.entities.RoomEntity
import java.lang.Exception


class RoomRepository(context: Context) {


    private var mScheduleDataBaseHelper: ScheduleDataBaseHelper = ScheduleDataBaseHelper(context)

    companion object {

        private var INSTANCE: RoomRepository? = null

        fun getInstance(context: Context): RoomRepository {
            if (INSTANCE == null) {
                INSTANCE = RoomRepository(context)
            }
            return INSTANCE as RoomRepository
        }
    }

    //get, getList, insert, update, delete

    fun get(pRoomId: Int): RoomEntity? {
        var room: RoomEntity? = null
        try {
            val cursor: Cursor
            val db = mScheduleDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.ROOM.COLUMNS.ID,
                    DataBaseConstants.ROOM.COLUMNS.DESCRIPTION,
                    DataBaseConstants.ROOM.COLUMNS.ALIAS,
                    DataBaseConstants.ROOM.COLUMNS.TYPE,
                    DataBaseConstants.ROOM.COLUMNS.LEVEL)

            val selection = "${DataBaseConstants.ROOM.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro recebendo o id como parametro

            val selectionArgs = arrayOf(pRoomId.toString())

            cursor = db.query(DataBaseConstants.ROOM.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.ID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.DESCRIPTION))
                val alias = cursor.getString(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.ALIAS))
                val type = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.TYPE))
                val level = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.LEVEL))

                room = RoomEntity(id, description, alias, type, level)

            }
            cursor.close()


        } catch (e: Exception) {
            return room
        }
        return room
    }

    fun getList(): MutableList<RoomEntity> {

        val list = mutableListOf<RoomEntity>()
        try {
            val cursor: Cursor
            val db = mScheduleDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.ROOM.TABLE_NAME} ", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.DESCRIPTION))
                    val alias = cursor.getString(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.ALIAS))
                    val type = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.TYPE))
                    val level = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.ROOM.COLUMNS.LEVEL))

                    list.add(RoomEntity(id, description, alias, type, level))

                }
            }
            cursor.close()
        } catch (e: Exception){
            return list
        }

        return list
    }

    fun insert(pRoomEntity: RoomEntity) {
        try {
            val db = mScheduleDataBaseHelper.writableDatabase


            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.ROOM.COLUMNS.DESCRIPTION, pRoomEntity.description)
            insertValues.put(DataBaseConstants.ROOM.COLUMNS.ALIAS, pRoomEntity.alias)
            insertValues.put(DataBaseConstants.ROOM.COLUMNS.TYPE, pRoomEntity.type)
            insertValues.put(DataBaseConstants.ROOM.COLUMNS.LEVEL, pRoomEntity.level)

            db.insert(DataBaseConstants.ROOM.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }
    }

    fun update(pRoomEntity: RoomEntity) {
        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.ROOM.COLUMNS.DESCRIPTION, pRoomEntity.description)
            updateValues.put(DataBaseConstants.ROOM.COLUMNS.ALIAS, pRoomEntity.alias)
            updateValues.put(DataBaseConstants.ROOM.COLUMNS.TYPE, pRoomEntity.type)
            updateValues.put(DataBaseConstants.ROOM.COLUMNS.LEVEL, pRoomEntity.level)

            val selection = "${DataBaseConstants.ROOM.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val selectionArgs = arrayOf(pRoomEntity.id.toString())

            db.update(DataBaseConstants.SCHEDULE.TABLE_NAME, updateValues, selection, selectionArgs)

        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(pRoomId: Int) {
        try {
            val db = mScheduleDataBaseHelper.writableDatabase

            val where = "${DataBaseConstants.ROOM.COLUMNS.ID} = ?" //Esse é o WHERE, que faz o filtro
            val whereArgs = arrayOf(pRoomId.toString())

            db.delete(DataBaseConstants.ROOM.TABLE_NAME, where, whereArgs)

        } catch (e: Exception) {
            throw e
        }

    }
}

