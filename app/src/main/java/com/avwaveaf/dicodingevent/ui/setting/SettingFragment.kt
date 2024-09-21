package com.avwaveaf.dicodingevent.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.avwaveaf.dicodingevent.R

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Set the initial theme based on saved preference
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        updateTheme(sharedPreferences.getBoolean(THEME_KEY, false))

    }



    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun updateTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            THEME_KEY -> {
                val isDarkMode = sharedPreferences?.getBoolean(THEME_KEY, false)
                isDarkMode?.let { updateTheme(it) }
            }

        }
    }





    companion object {
        const val THEME_KEY = "theme_preference"
        const val NOTIFICATION_PREFERENCES_KEY = "notification_reminder"
        const val REQUEST_CODE_NOTIFICATION_PERMISSION: Int = 100
    }
}
