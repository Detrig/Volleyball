package com.example.disputer.auth.ui_elements

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.not

class ToastUi {
    fun assertToastDisplayed(message: String) {
        onView(withText(message))
            .inRoot(withDecorView(not(getActivityInstance()?.window?.decorView)))
            .check(matches(isDisplayed()))
    }

    private fun getActivityInstance(): Activity? {
        val activity = arrayOf<Activity?>(null)
        onView(isRoot()).check { view, _ ->
            activity[0] = view.context as? Activity
        }
        return activity[0]
    }
}