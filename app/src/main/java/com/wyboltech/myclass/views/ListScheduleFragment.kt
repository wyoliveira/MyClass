package com.wyboltech.myclass.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.wyboltech.myclass.R
import com.wyboltech.myclass.adapter.ScheduleListAdapter
import com.wyboltech.myclass.business.ScheduleBusiness
import com.wyboltech.myclass.constants.MyClassConstants
import com.wyboltech.myclass.entities.OnInteractionScheduleByMenuListener
import java.util.*

class ListScheduleFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mRecyclerScheduleList: RecyclerView
    private lateinit var mScheduleBusiness: ScheduleBusiness
    private lateinit var m2Listener: OnInteractionScheduleByMenuListener
    private var dayofweek = 0

    companion object {
        fun newInstance(weekDay: Int): ListScheduleFragment {
            val args = Bundle()
            args.putInt(MyClassConstants.WEEKDAYFILTER.KEY, weekDay)

            val fragment = ListScheduleFragment()
            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dayofweek = it.getInt(MyClassConstants.WEEKDAYFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_list_schedule, container, false)

        mContext = rootView.context
        mScheduleBusiness = ScheduleBusiness(mContext)

        m2Listener = object : OnInteractionScheduleByMenuListener {
            override fun onEditClickItemMenu(scheduleId: Int) {
                val bundle = Bundle()
                bundle.putInt(MyClassConstants.BUNDLE.SCHEDULEID, scheduleId)
                val intent = Intent(mContext, ScheduleNewFormActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClickItemMenu(scheduleId: Int) {
                mScheduleBusiness.delete(scheduleId)
                loadSchedules()
                Toast.makeText(mContext,"Horário removido com sucesso!", Toast.LENGTH_LONG).show()

            }

            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId){
                    R.id.menu_item_edit_schedule -> {
                        getOnMenuItemClickListener()
                        return true

                    }
                    R.id.menu_item_delete -> {
                        getOnMenuItemClickListener()
                        return true
                    }
                }
                getOnMenuItemClickListener()
                return false
            }

        }

        val scheduleList = mScheduleBusiness.getList(dayofweek)
        //1 - Obter o elemento
        mRecyclerScheduleList = rootView.findViewById(R.id.recyclerScheduleList)
        //2 - Definir um adapter com os itens da listagem
        mRecyclerScheduleList.adapter = ScheduleListAdapter(scheduleList, m2Listener, dayofweek)
        //3 - Definir um layout
        mRecyclerScheduleList.layoutManager = LinearLayoutManager(mContext)

        unregisterForContextMenu(rootView)

        return rootView
    }


    override fun onResume() {
        super.onResume()
        loadSchedules()
    }

    private fun loadSchedules() {
        mRecyclerScheduleList.adapter = ScheduleListAdapter(mScheduleBusiness.getList(dayofweek), m2Listener, dayofweek)
    }

}
