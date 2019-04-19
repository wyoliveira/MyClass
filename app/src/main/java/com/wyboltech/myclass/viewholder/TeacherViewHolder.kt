package com.wyboltech.myclass.viewholder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.wyboltech.myclass.R
import com.wyboltech.myclass.entities.OnInteractionItemTeacher
import com.wyboltech.myclass.entities.TeacherEntity
import kotlinx.android.synthetic.main.row_teacher_item_list.view.*

class TeacherViewHolder (itemView: View, val listener: OnInteractionItemTeacher, val context: Context): RecyclerView.ViewHolder(itemView){

    private val mTeacherName: TextView = itemView.findViewById(R.id.name_teacher)
    private val mTeacherEmail: TextView = itemView.findViewById(R.id.teacher_academic_email)
    private val mTeacherGroupInfo: RelativeLayout = itemView.findViewById(R.id.layout_agroup_info_teacher)
    private val mDeleteTeacherButton: ImageView = itemView.findViewById(R.id.img_delete_teacher_item)

    fun bindData(teacher: TeacherEntity) {

        mTeacherName.text = teacher.name
        mTeacherEmail.text = teacher.email
        mDeleteTeacherButton.setOnClickListener { showConfirmationDialog(teacher)}

    }

    private fun showConfirmationDialog(pTeacher: TeacherEntity){
        AlertDialog.Builder(context)
                .setTitle("Remoção de Professor")
                .setMessage("Você deseja remover ${pTeacher.name}?")
                .setIcon(R.drawable.ic_delete_teacher)
                .setPositiveButton("Excluir") { dialog: DialogInterface, which -> listener.deleteTeacher(pTeacher.id)}
                //.setPositiveButton("Remover", handleRemoval(listener, task.id))
                .setNegativeButton(R.string.cancelar, null).show()

    }

}
