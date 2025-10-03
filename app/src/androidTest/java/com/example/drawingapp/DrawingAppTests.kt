package com.example.drawingapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.drawingapp.screens.BrushType
import com.example.drawingapp.screens.DrawScreenPortrait
import com.example.drawingapp.screens.DrawingViewModel
import com.example.drawingapp.screens.Stroke

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

    @Test
    fun testBlueButtonClickChangesColor() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        composeTestRule.onNodeWithTag("blueButton").performClick()

        assertEquals(Color.Blue, testViewModel.color.value)
    }

    @Test
    fun testGreenButtonClickChangesColor() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        composeTestRule.onNodeWithTag("greenButton").performClick()

        assertEquals(Color.Green, testViewModel.color.value)
    }

    @Test
    fun testLineButtonClickChangesBrushType() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        composeTestRule.onNodeWithTag("lineButton").performClick()

        assertEquals(BrushType.LINE, testViewModel.brushType.value)
    }

    @Test
    fun testRectangleButtonClickChangesBrushType() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        composeTestRule.onNodeWithTag("rectangleButton").performClick()

        assertEquals(BrushType.RECTANGLE, testViewModel.brushType.value)
    }
    @Test
    fun testCircleButtonClickChangesBrushType() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        composeTestRule.onNodeWithTag("circleButton").performClick()

        assertEquals(BrushType.CIRCLE, testViewModel.brushType.value)
    }

    @Test
    fun testCurrentStrokeUpdates() {
        val testViewModel = DrawingViewModel()

        composeTestRule.setContent {
            DrawScreenPortrait(testViewModel)
        }

        val offset1 = Offset(10f, 20f)
        val offset2 = Offset(30f, 40f)

        testViewModel.startStroke(offset1)

        assertEquals(offset1, testViewModel.currentStroke.value[0])

        testViewModel.addPoint(offset2)

        assertEquals(offset2, testViewModel.currentStroke.value[1])

        val offsets = listOf<Offset>(offset1, offset2)

        val stroke = Stroke(
            brushType = testViewModel.brushType.value,
            color = testViewModel.color.value,
            size = testViewModel.brushSize.value,
            points = offsets
        )

        assertEquals(stroke, testViewModel.strokes.value[0])

        testViewModel.endStroke()

        assertEquals(emptyList<Offset>(), testViewModel.currentStroke.value)
    }
}
