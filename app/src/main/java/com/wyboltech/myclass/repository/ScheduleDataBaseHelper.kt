package com.wyboltech.myclass.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.wyboltech.myclass.constants.DataBaseConstants

class ScheduleDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
        ${DataBaseConstants.ROOM.COLUMNS.DESCRIPTION} TEXT,
        ${DataBaseConstants.ROOM.COLUMNS.ALIAS} TEXT,
        ${DataBaseConstants.ROOM.COLUMNS.TYPE} INTEGER,
        ${DataBaseConstants.ROOM.COLUMNS.LEVEL} INTEGER
        );"""

    private val createTableTeacher = """CREATE TABLE ${DataBaseConstants.TEACHER.TABLE_NAME} (
        ${DataBaseConstants.TEACHER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.TEACHER.COLUMNS.USERID} INTEGER,
        ${DataBaseConstants.TEACHER.COLUMNS.NAME} TEXT,
        ${DataBaseConstants.TEACHER.COLUMNS.EMAIL} TEXT
    );"""

    private val createRoomLabsDefault = """INSERT INTO ${DataBaseConstants.ROOM.TABLE_NAME} (
	${DataBaseConstants.ROOM.COLUMNS.DESCRIPTION},
	${DataBaseConstants.ROOM.COLUMNS.ALIAS},
	${DataBaseConstants.ROOM.COLUMNS.TYPE},
	${DataBaseConstants.ROOM.COLUMNS.LEVEL}) VALUES
('Lab de Informática I', 'LAB 1', 2, 1),
('Lab de Informática II', 'LAB 2', 2, 3),
('Lab de Informática III', 'LAB 3', 2, 3),
('Lab de Informática IV', 'LAB 4', 2, 3),
('Lab de Informática V', 'LAB 5', 2, 2),
('Lab de Fís e Eletricidade I', 'LAB FIS ELETRIC I', 2, 2),
('Lab de Fís e Eletricidade II', 'LAB FIS ELETRIC II', 2, 2),
('Lab de Química Geral', 'LAB QUIM GERAL', 2, 1),
('Lab de Física e Química', 'LAB FIS QUIM', 2, 1),
('Lab de Inform e Redes', 'LAB INFO E REDES', 2, 3),
('Lab de Hardware', 'LAB HARDWR', 2, 2);
    """

    private val createRoomRoomDefault = """INSERT INTO ${DataBaseConstants.ROOM.TABLE_NAME} (
	${DataBaseConstants.ROOM.COLUMNS.DESCRIPTION},
	${DataBaseConstants.ROOM.COLUMNS.ALIAS},
	${DataBaseConstants.ROOM.COLUMNS.TYPE},
	${DataBaseConstants.ROOM.COLUMNS.LEVEL}) VALUES
('Sala 1', 'SALA 1', 1, 1),
('Sala 2', 'SALA 2', 1, 1),
('Sala 3', 'SALA 3', 1, 1),
('Sala 4', 'SALA 4', 1, 1),
('Sala 5', 'SALA 5', 1, 1),
('Sala 6', 'SALA 6', 1, 1),
('Sala 7', 'SALA 7', 1, 1),
('Sala 8', 'SALA 8', 1, 1),
('Sala 9', 'SALA 9', 1, 2),
('Sala 10', 'SALA 10', 1, 2),
('Sala 11', 'SALA 11', 1, 2),
('Sala 12', 'SALA 12', 1, 2),
('Sala 13', 'SALA 13', 1, 2),
('Sala 14', 'SALA 14', 1, 2),
('Sala 15', 'SALA 15', 1, 2),
('Sala 16', 'SALA 16', 1, 2),
('Sala 17', 'SALA 17', 1, 3),
('Sala 18', 'SALA 18', 1, 3),
('Sala 19', 'SALA 19', 1, 3),
('Sala 20', 'SALA 20', 1, 3),
('Sala 21', 'SALA 21', 1, 3),
('Sala 22', 'SALA 22', 1, 3),
('Sala 23', 'SALA 23', 1, 3),
('Sala 24', 'SALA 24', 1, 3);
"""

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTableSchedule = "drop table if exists ${DataBaseConstants.SCHEDULE.TABLE_NAME}"
    private val deleteTableRoom = "drop table if exists ${DataBaseConstants.ROOM.TABLE_NAME}"
    private val deleteTableTeacher = "drop table if exists ${DataBaseConstants.TEACHER.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTableSchedule)
        db.execSQL(createTableRoom)
        db.execSQL(createTableTeacher)

        db.execSQL(createRoomLabsDefault)
        db.execSQL(createRoomRoomDefault)

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
        db.execSQL(createRoomLabsDefault)
        db.execSQL(createRoomRoomDefault)

    }
}