package com.wyboltech.myclass.repository

import com.wyboltech.myclass.entities.RoomEntity

class RoomCacheConstants private constructor(){

    companion object {

        fun setCache(list: List<RoomEntity>){
            for (item in list){
                mRoomCache.put(item.id, item.alias.capitalize())
            }
        }

        fun getRoomDescription(id: Int): String {
            if(mRoomCache[id] == null){
                return ""
            }
            return mRoomCache[id].toString()
        }

        private val mRoomCache = hashMapOf<Int, String>()
    }
}