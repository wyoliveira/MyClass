package com.wyboltech.myclass.views

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.wyboltech.myclass.R

class SettingsFragment: PreferenceFragmentCompat (){
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
}