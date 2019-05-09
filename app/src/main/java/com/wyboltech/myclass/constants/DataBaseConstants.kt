package com.wyboltech.myclass.constants

class DataBaseConstants {
    object USER {
        const val TABLE_NAME = "user"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val EMAIL = "email"
            const val PASSWORD = "password"
        }
    }
    object SCHEDULE {
        const val TABLE_NAME = "schedule"

        object COLUMNS {
            const val ID = "id"
            const val USERID = "userid"
            const val SUBJECT = "subject"
            const val TEACHER = "teacher"
            const val DAYOFWEEK = "dayofweek"
            const val ROOMID = "roomid"
            const val INITIAL_DATE = "initialdate"
            const val FINAL_DATE = "finaldate"
        }
    }
    object ROOM {
        const val TABLE_NAME = "room"

        object COLUMNS {
            const val ID = "id"
            const val DESCRIPTION = "description"
            const val ALIAS = "alias"
            const val TYPE = "type"
            const val LEVEL = "level"

        }
    }
    object TEACHER {
        const val TABLE_NAME = "teacher"

        object COLUMNS {
            const val ID = "id"
            const val USERID = "user_id"
            const val NAME = "name"
            const val EMAIL = "email"

        }
    }
}