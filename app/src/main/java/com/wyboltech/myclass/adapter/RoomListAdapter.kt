package com.wyboltech.myclass.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.RoomEntity
import com.wyboltech.myclass.viewholder.RoomViewHolder

class RoomListAdapter (val roomList: List<RoomEntity>): RecyclerView.Adapter<RoomViewHolder> (){
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.bindData(room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_room_item_list, parent, false)

        return RoomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roomList.count()

    }

}