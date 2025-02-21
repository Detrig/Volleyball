package com.example.avito2_0.auth.ui_elements.abstract

import androidx.test.espresso.ViewInteraction

abstract class AbstractButton(
    protected val interaction : ViewInteraction
) {
    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }
}