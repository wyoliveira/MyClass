package com.wyboltech.myclass.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.wyboltech.myclass.constants.DataBaseConstants
import com.wyboltech.myclass.entities.UserEntity
import java.lang.Exception


class UserRepository private constructor(context: Context) {

    private var mScheduleDataBaseHelper: ScheduleDataBaseHelper = ScheduleDataBaseHelper(context)

    companion object {

        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }
    }

    fun insert(pName: String, pEmail: String, pPassword: String): Int {
        val db = mScheduleDataBaseHelper.writableDatabase


        val insertValues = ContentValues()

        insertValues.put(DataBaseConstants.USER.COLUMNS.NAME, pName)
        insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, pEmail)
        insertValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, pPassword)

        return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues).toInt()

    }

    fun get(pEmail: String, pPassword: String): UserEntity? {
        var userEntity: UserEntity? = null
        try {
            val cursor: Cursor
            val db = mScheduleDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID,
                    DataBaseConstants.USER.COLUMNS.NAME,
                    DataBaseConstants.USER.COLUMNS.EMAIL,
                    DataBaseConstants.USER.COLUMNS.PASSWORD)

            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMNS.PASSWORD} = ?" //Esse é o WHERE, que faz o filtro
            val selectionArgs = arrayOf(pEmail, pPassword)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if( cursor.count > 0 ){
                cursor.moveToFirst()

                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME))
                val email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL))

                userEntity =  UserEntity(userId, name, email)
            }
            cursor.close()

        } catch (e: Exception) {
            return userEntity
        }
        return userEntity

    }

    fun isEmailExistent(pEmail: String): Boolean {
        var ret: Boolean

        try {
            val cursor: Cursor
            val db = mScheduleDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID)//Esse cara identifica a unicidade do registro, por isso usa o ID

            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ?" //Esse é o WHERE, que faz o filtro

            val selectionArgs = arrayOf(pEmail)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            //db.rawQuery("select * from user where email = Wesley", null) //Método mais hardcode
            ret = cursor.count > 0

            cursor.close()
        } catch (e: Exception) {
            throw e
        }
        return ret
    }
}