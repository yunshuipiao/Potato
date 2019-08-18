package com.swensun.time.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.swensun.time.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}