package com.wyboltech.myclass.business

import android.content.Context
import com.wyboltech.myclass.entities.RoomEntity
import com.wyboltech.myclass.repository.RoomRepository
import java.lang.Exception

class RoomBusiness (context: Context){
    private val mRoomRepository: RoomRepository = RoomRepository.getInstance(context)

    fun get(id: Int) = mRoomRepository.get(id)

    fun getList(): MutableList<RoomEntity> = mRoomRepository.getList()

    fun insert(room: RoomEntity) = mRoomRepository.insert(room)

    fun update(room: RoomEntity) = mRoomRepository.update(room)

    fun delete(roomId: Int) = mRoomRepository.delete(roomId)

    /*fun createRoomDefault(): MutableList<RoomEntity> {

        var listMock = mutableListOf<RoomEntity>()
        try {
            for (i in 1..24) {
                val roomMock = RoomEntity(i, "Sala $i")
                if(mRoomRepository.get(i) != roomMock) {
                    mRoomRepository.insert(roomMock)
                    listMock.add(roomMock)
                }
            }

        }catch (e: Exception){
            return listMock
        }
        return listMock
    }*/

}