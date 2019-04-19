package com.wyboltech.myclass.views

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.wyboltech.myclass.R
import com.wyboltech.myclass.adapter.TeacherListAdapter
import com.wyboltech.myclass.business.TeacherBusiness

private const val ARG_PARAM1 = "param1"

class TeacherListFragment : Fragment() {
    private var param1: String? = null
    private lateinit var mContext: Context
    private lateinit var mRecyclerTeacherList: RecyclerView
    private lateinit var mTeacherBusiness: TeacherBusiness

    companion object {
        fun newInstance(): TeacherListFragment {
            val args = Bundle()
            val fragment = TeacherListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list_teacher, container, false)

        mContext = rootView.context
        mTeacherBusiness = TeacherBusiness(mContext)
        // 1 Obter o elemento
        mRecyclerTeacherList = rootView.findViewById(R.id.recyclerTeacherList)
        val teacherList = mTeacherBusiness.getList()
        // 2 Definir um adapter com os itens de listagem
        //mRecyclerTeacherList.adapter = TeacherListAdapter(teacherList)
        // 3 Definir um layout
        mRecyclerTeacherList.layoutManager = LinearLayoutManager(mContext)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTeachers()
    }

    private fun loadTeachers() {
        //mRecyclerTeacherList.adapter = TeacherListAdapter(mTeacherBusiness.getList(), m)
    }

}
