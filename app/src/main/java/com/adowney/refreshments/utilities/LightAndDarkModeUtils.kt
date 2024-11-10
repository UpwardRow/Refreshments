package com.adowney.refreshments.utilities

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.WindowInsetsController

object LightAndDarkModeUtils {

    fun setStatusBarIconColour(activity: Activity){
        // Check if Dark Mode is enabled
        val isDarkMode = isDarkMode(activity)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsController = activity.window.insetsController
            if (isDarkMode) {
                // Dark mode: set icons to light
                insetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                // Light mode: set icons to dark
                insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else {
            @Suppress("DEPRECATION")
            activity.window.decorView.systemUiVisibility = if (isDarkMode) {
                // Dark mode: reset to default (light icons)
                0
            } else {
                // Light mode: dark icons
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun isDarkMode(activity: Activity): Boolean {
        return (activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}