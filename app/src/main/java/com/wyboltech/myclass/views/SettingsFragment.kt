package com.wyboltech.myclass.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.wyboltech.myclass.R


class  SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener{
    
    companion object {
        fun newInstance(): SettingsFragment {
            val args: Bundle = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args

            return fragment
        }
    }
    override fun onCreatePreferences(bundle: Bundle?, key: String?) {
        addPreferencesFromResource(R.xml.preferences_settings)
    }

    override fun onResume() {
        super.onResume()

        preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

}