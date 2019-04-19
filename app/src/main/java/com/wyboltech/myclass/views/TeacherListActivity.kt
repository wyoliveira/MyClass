package com.wyboltech.myclass.views

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.adapter.TeacherListAdapter
import com.wyboltech.myclass.business.TeacherBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.OnInteractionItemTeacher
import com.wyboltech.myclass.entities.TeacherEntity
import com.wyboltech.myclass.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_teacher_list.*
import kotlinx.android.synthetic.main.dialog_add_teacher.view.*
import kotlinx.android.synthetic.main.row_teacher_item_list.*


class TeacherListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mTeacherBusiness: TeacherBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mRecyclerTeacherList: RecyclerView
    private lateinit var mListener: OnInteractionItemTeacher
    private var mTeacherId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_list)


        mTeacherBusiness = TeacherBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
        setupRecycler(this)
        setListeners()
    }

    private fun setupRecycler(context: Context) {

        val layoutManager = LinearLayoutManager(this)
        mRecyclerTeacherList = findViewById(R.id.recyclerTeacherList)
        mRecyclerTeacherList.layoutManager = layoutManager
        val teacherList = mTeacherBusiness.getList()

        mListener = object : OnInteractionItemTeacher {
            override fun deleteTeacher(teacherId: Int) {
                mTeacherBusiness.delete(teacherId)
                loadTeachers()
                Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_LONG).show()
            }
        }
        mRecyclerTeacherList.adapter = TeacherListAdapter(teacherList, mListener)

        mRecyclerTeacherList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    override fun onClick(view: View) {
        if (view.id == R.id.floatAddTeacher) {
            showFormTeacher()
        }
    }

    private fun showFormTeacher() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_teacher, null)
        AlertDialog.Builder(this)
                .setTitle("Adicionar professor")
                .setView(view)
                .setPositiveButton("OK") { dialog, which ->
                    handleTeacher(view.edit_title_teacher.text.toString(), view.edit_email_academic_teacher.text.toString())
                }
                .setNegativeButton(getString(R.string.cancelar), null)
                .show()


    }

    private fun handleTeacher(nameTeacher: String, emailTeacher: String) {
        try {

            val userId = mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID).toInt()
            val teacher = TeacherEntity(mTeacherId, userId, nameTeacher, emailTeacher)

            if (mTeacherId == 0) {
                mTeacherBusiness.insert(teacher)
                Toast.makeText(this, getString(R.string.professor_incluido), Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
        loadTeachers()
    }

    override fun onResume() {
        super.onResume()
        loadTeachers()
    }

    private fun loadTeachers() {
        mRecyclerTeacherList.adapter = TeacherListAdapter(mTeacherBusiness.getList(), mListener)
    }

    private fun setListeners() {
        floatAddTeacher.setOnClickListener(this)
    }
}
