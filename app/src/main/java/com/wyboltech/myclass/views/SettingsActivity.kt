package com.wyboltech.myclass.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wyboltech.myclass.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val layoutManager = supportFragmentManager
        val fragmentSettings = SettingsFragment.newInstance()
        if(fragmentSettings != null){
            layoutManager.beginTransaction().replace(R.id.fragment_settings, fragmentSettings).commit()
        }
    }
}
