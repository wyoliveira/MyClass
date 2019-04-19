package com.wyboltech.myclass.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.wyboltech.myclass.views.ListScheduleFragment

class ScreenSlidePageAdapter (fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int = 6


    override fun getItem(position: Int): Fragment {

        if (position == 0) {
            return ListScheduleFragment.newInstance(1)
        }
        if (position == 1){
            return ListScheduleFragment.newInstance(2)
        }
        if (position == 2) {
            return ListScheduleFragment.newInstance(3)
        }
        if (position == 3){
            return ListScheduleFragment.newInstance(4)
        }
        if (position == 4) {
            return ListScheduleFragment.newInstance(5)
        }
        if (position == 5) {
            return ListScheduleFragment.newInstance(6)
        }
        return ListScheduleFragment.newInstance(1)
    }
}