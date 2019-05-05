package com.wyboltech.myclass.views

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
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
import com.wyboltech.myclass.util.ValidationException
import kotlinx.android.synthetic.main.activity_teacher_list.*
import kotlinx.android.synthetic.main.dialog_add_teacher.view.*
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.*
import android.text.TextUtils
import android.view.MenuItem
import android.widget.EditText


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

        val layoutManager = GridLayoutManager(this, 1)
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
            showFormTeacherValidating()
        }
    }

    private fun showFormTeacherValidating() {
        val mView = LayoutInflater.from(this).inflate(R.layout.dialog_add_teacher, null)
        val alertDialogBuilderForm = AlertDialog.Builder(this)

        val acceptUserInput: EditText = mView.findViewById(R.id.edit_title_teacher)

        alertDialogBuilderForm.setView(mView)

        alertDialogBuilderForm
                .setCancelable(false)
                .setTitle("Cadastrar professor")
                .setPositiveButton(getString(R.string.salvar), null)
                .setNegativeButton(getString(R.string.cancelar), null)

        val alertDialogAndroid = alertDialogBuilderForm.create()
        alertDialogAndroid.show()
        alertDialogAndroid.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (!acceptUserInput.text.isBlank()) {
                handleTeacher(mView.edit_title_teacher.text.toString(), mView.edit_email_academic_teacher.text.toString())
                alertDialogAndroid.dismiss()
            } else {
                acceptUserInput.error = "Campo obrigatório!"
            }
        }
        alertDialogAndroid.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            alertDialogAndroid.cancel()
        }

    }

    private fun handleTeacher(pNameTeacher: String, pEmailTeacher: String) {
        try {
            val nameTeacher = pNameTeacher.trim()
            val emailTeacher = pEmailTeacher.trim()
            val userId = mSecurityPreferences.getStoredString(MyClassConstants.KEY.USER_ID).toInt()
            val teacher = TeacherEntity(mTeacherId, userId, nameTeacher, emailTeacher)

            if (mTeacherId == 0) {
                mTeacherBusiness.insert(teacher)
            }
        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
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
