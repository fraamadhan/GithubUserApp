package com.example.githubapp.ui.activity

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.databinding.ActivitySettingBinding
import com.example.githubapp.ui.factory.PreferencesViewModelFactory
import com.example.githubapp.ui.viewmodel.SettingViewModel
import com.example.githubapp.util.SettingPreferences
import com.example.githubapp.util.dataStore

class SettingActivity : AppCompatActivity() {

    private var binding : ActivitySettingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this){isDarkModeActive ->
           binding?.apply {
               if (isDarkModeActive) {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                   switchTheme.isChecked = true
               } else {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                   switchTheme.isChecked = false
               }
           }
        }
        binding?.switchTheme?.setOnCheckedChangeListener{_:CompoundButton?, isChecked:Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}