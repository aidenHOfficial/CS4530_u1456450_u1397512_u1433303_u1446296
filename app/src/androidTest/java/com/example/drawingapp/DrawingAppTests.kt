package com.example.drawingapp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.drawingapp.screens.DrawScreenPortrait
import com.example.drawingapp.screens.DrawingScreen
import com.example.drawingapp.screens.DrawingViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class DrawingAppTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRedButtonClickChangesColor() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        composeTestRule.onNodeWithTag("redButton").performClick()

        assertEquals(Color.Red, testViewModel.color.value)
    }
}
