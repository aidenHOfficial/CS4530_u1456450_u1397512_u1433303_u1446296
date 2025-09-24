package com.example.drawingapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class BrushType {
    LINE, CIRCLE, RECTANGLE
}

class DrawingViewModel : ViewModel() {
    private val _strokes = MutableStateFlow<List<List<Offset>>>(emptyList())
    val strokes: StateFlow<List<List<Offset>>> = _strokes
    private val _currentStroke = MutableStateFlow<List<Offset>>(emptyList())
    val currentStroke: StateFlow<List<Offset>> = _currentStroke
    private val _brushType = MutableStateFlow(BrushType.CIRCLE)
    val brushType: StateFlow<BrushType> = _brushType

    fun startStroke(offset: Offset) {
        _currentStroke.value = listOf(offset)
        _strokes.value = _strokes.value + listOf(_currentStroke.value)
    }

    fun addPoint(offset: Offset) {
        _currentStroke.value = _currentStroke.value + offset
        _strokes.value = _strokes.value.dropLast(1) + listOf(_currentStroke.value)
    }

    fun endStroke() {
        _currentStroke.value = emptyList()
    }

    fun setBrushType(type: BrushType) {
        _brushType.value = type
    }
}

@Composable
fun DrawingScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val drawingViewModel: DrawingViewModel = viewModel()

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        DrawScreenPortrait(drawingViewModel)
    }
    else {
        DrawScreenLandscape(drawingViewModel)
    }
}

@Composable
fun DrawScreenPortrait(viewModel: DrawingViewModel) {
    Column (
        Modifier.fillMaxWidth().padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,)
    {
        DrawingCanvas(viewModel)
    }
}

@Composable
fun DrawScreenLandscape(viewModel: DrawingViewModel) {
    DrawingCanvas(viewModel)
}

@Composable
fun DrawingCanvas(viewModel: DrawingViewModel) {
    val strokes by viewModel.strokes.collectAsState()
    val currentStroke by viewModel.currentStroke.collectAsState()
    val brushType by viewModel.brushType.collectAsState()

    Canvas(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp)
            .border(width=1.dp, color = Color.Black)
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        viewModel.startStroke(offset)
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        viewModel.addPoint(change.position)
                    },
                    onDragEnd = {
                        viewModel.endStroke()
                    }
                )
            }
    ) {
        strokes.forEach { stroke ->
            when (brushType) {
                BrushType.LINE -> {
                    for (i in 0 until stroke.size - 1) {
                        drawLine(Color.Black, stroke[i], stroke[i + 1], strokeWidth = 4f)
                    }
                }
                BrushType.CIRCLE -> {
                    stroke.forEach { point ->
                        drawCircle(Color.Red, radius = 15f, center = point)
                    }
                }
                BrushType.RECTANGLE -> {
                    stroke.forEach { point ->
                        drawRect(
                            Color.Black,
                            topLeft = Offset(point.x - 8f, point.y - 8f),
                            size = Size(16f, 16f)
                        )
                    }
                }
            }
        }
    }
}