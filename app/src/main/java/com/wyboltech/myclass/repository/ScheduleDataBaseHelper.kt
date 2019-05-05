package com.wyboltech.myclass.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.wyboltech.myclass.constants.DataBaseConstants

class ScheduleDataBaseHelper (context: Context): SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private val DATABASE_NAME = "myclass.db"
        private val DATABASE_VERSION = 1
    }

    private val createTableUser = """ CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME} (
        ${DataBaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY  AUTOINCREMENT,
        ${DataBaseConstants.USER.COLUMNS.NAME} TEXT,
        ${DataBaseConstants.USER.COLUMNS.EMAIL} TEXT,
        ${DataBaseConstants.USER.COLUMNS.PASSWORD} TEXT
        );"""

    private val createTableSchedule = """CREATE TABLE ${DataBaseConstants.SCHEDULE.TABLE_NAME} (
        ${DataBaseConstants.SCHEDULE.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.SCHEDULE.COLUMNS.USERID} INTEGER,
        ${DataBaseConstants.SCHEDULE.COLUMNS.SUBJECT} TEXT,
        ${DataBaseConstants.SCHEDULE.COLUMNS.TEACHER} TEXT,
        ${DataBaseConstants.SCHEDULE.COLUMNS.DAYOFWEEK} INTEGER,
        ${DataBaseConstants.SCHEDULE.COLUMNS.ROOMID} INTEGER,
        ${DataBaseConstants.SCHEDULE.COLUMNS.INITIAL_DATE} TEXT,
        ${DataBaseConstants.SCHEDULE.COLUMNS.FINAL_DATE} TEXT
        );"""

    private val createTableRoom = """ CREATE TABLE ${DataBaseConstants.ROOM.TABLE_NAME} (
        ${DataBaseConstants.ROOM.COLUMNS.ID} INTEGER PRIMARY KEY  AUTOINCREMENT,
        ${DataBaseConstants.ROOM.COLUMNS.DESCRIPTION} TEXT
        );"""

    private val createTableTeacher = """CREATE TABLE ${DataBaseConstants.TEACHER.TABLE_NAME} (
        ${DataBaseConstants.TEACHER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.TEACHER.COLUMNS.USERID} INTEGER,
        ${DataBaseConstants.TEACHER.COLUMNS.NAME} TEXT,
        ${DataBaseConstants.TEACHER.COLUMNS.EMAIL} TEXT
    );"""

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTableSchedule = "drop table if exists ${DataBaseConstants.SCHEDULE.TABLE_NAME}"
    private val deleteTableRoom = "drop table if exists ${DataBaseConstants.ROOM.TABLE_NAME}"
    private val deleteTableTeacher = "drop table if exists ${DataBaseConstants.TEACHER.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTableSchedule)
        db.execSQL(createTableRoom)
        db.execSQL(createTableTeacher)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(deleteTableSchedule)
        db.execSQL(deleteTableUser)
        db.execSQL(deleteTableRoom)
        db.execSQL(deleteTableTeacher)

        db.execSQL(createTableRoom)
        db.execSQL(createTableUser)
        db.execSQL(createTableSchedule)
        db.execSQL(createTableTeacher)

    }
}