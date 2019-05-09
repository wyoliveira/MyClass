package com.wyboltech.myclass.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.RoomEntity

class RoomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val mImageTypeIcon: ImageView = itemView.findViewById(R.id.icon_room_type)
    private val mAliasRoom: TextView = itemView.findViewById(R.id.alias_room)
    private val mRoomDescription: TextView = itemView.findViewById(R.id.room_description)
    private val mRoomLevel: TextView = itemView.findViewById(R.id.text_level_room)

    fun bindData(room: RoomEntity){

    }
}