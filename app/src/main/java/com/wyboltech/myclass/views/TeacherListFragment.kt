package com.wyboltech.myclass.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.adapter.TeacherListAdapter
import com.wyboltech.myclass.business.TeacherBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.OnInteractionItemTeacher
import com.wyboltech.myclass.entities.TeacherEntity
import com.wyboltech.myclass.util.SecurityPreferences

class TeacherListFragment: Fragment() {
    private lateinit var mContext: Context
    private lateinit var mRecyclerTeacherList: RecyclerView
    private lateinit var mTeacherBusiness: TeacherBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener: OnInteractionItemTeacher

    companion object {
        fun newInstance(): TeacherListFragment {
            val args: Bundle = Bundle()
            val fragment = TeacherListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //mTeacherFilter = it.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list_teacher, container, false)

        mContext = rootView.context

        mTeacherBusiness = TeacherBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)
        val teacherList = mTeacherBusiness.getList()

        mListener = object : OnInteractionItemTeacher {
            override fun deleteTeacher(teacherId: Int) {
                mTeacherBusiness.delete(teacherId)
                loadTeachers()
                Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_LONG).show()
            }
        }
        // 1 Obter o elemento
        mRecyclerTeacherList = rootView.findViewById(R.id.recyclerTeacherList)

        // 2 Definir um adapter com os itens de listagem

        mRecyclerTeacherList.adapter = TeacherListAdapter(teacherList, mListener)
        // 3 Definir um layout
        mRecyclerTeacherList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTeachers()
    }
    private fun loadTeachers() {
        mRecyclerTeacherList.adapter = TeacherListAdapter(mTeacherBusiness.getList(), mListener)
    }

}
