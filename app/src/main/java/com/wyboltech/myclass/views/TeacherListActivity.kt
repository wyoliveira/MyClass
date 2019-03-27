package com.wyboltech.myclass.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wyboltech.myclass.R

class TeacherListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_list)

        val fragment = TeacherListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frame_content_teacher_activity, fragment).commit()
    }
}
