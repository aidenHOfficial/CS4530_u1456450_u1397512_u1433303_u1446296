package com.example.drawingapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.drawingapp.R
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class BrushType {
    LINE, CIRCLE, RECTANGLE
}

data class Stroke(
    val brushType: BrushType,
    val color: Color,
    val size: Float,
    val points: List<Offset>
)

class DrawingViewModel : ViewModel() {
    private val _strokes = MutableStateFlow<List<Stroke>>(mutableListOf())
    val strokes: StateFlow<List<Stroke>> = _strokes
    private val _currentStroke = MutableStateFlow<List<Offset>>(emptyList())
    val currentStroke: StateFlow<List<Offset>> = _currentStroke
    private val _brushType = MutableStateFlow(BrushType.CIRCLE)
    val brushType: StateFlow<BrushType> = _brushType
    private val _brushSize = MutableStateFlow(4f)
    val brushSize: StateFlow<Float> = _brushSize
    private val _color = MutableStateFlow(Color.Black)
    val color: StateFlow<Color> = _color

    fun startStroke(offset: Offset) {
        _currentStroke.value = listOf(offset)
        _strokes.value = _strokes.value + Stroke(brushType = brushType.value, color = _color.value, size = brushSize.value, points = currentStroke.value)
    }

    fun addPoint(offset: Offset) {
        _currentStroke.value = _currentStroke.value + offset
        _strokes.value = _strokes.value.dropLast(1) + Stroke(brushType = brushType.value, color = _color.value, size = brushSize.value, points = currentStroke.value)
    }

    fun endStroke() {
        _currentStroke.value = emptyList()
    }

    fun setBrushType(type: BrushType) {
        _brushType.value = type
    }

    fun setColor(color: Color) {
        _color.value = color
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
        Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,)
    {
        BrushMenu(viewModel)
        DrawingCanvas(viewModel)
    }
}

@Composable
fun DrawScreenLandscape(viewModel: DrawingViewModel) {
    DrawingCanvas(viewModel)
    BrushMenu(viewModel)
}

@Composable
fun BrushMenu(viewModel: DrawingViewModel) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val drawingViewModel: DrawingViewModel = viewModel()

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        BrushMenuPortrait(drawingViewModel)
    }
    else {
        BrushMenuLandscape(drawingViewModel)
    }
}

@Composable
fun BrushMenuPortrait(viewModel: DrawingViewModel) {
    val buttonSize = 60.dp
    var pickingColor by remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                viewModel.setBrushType(BrushType.LINE)
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .size(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.lineicon),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setBrushType(BrushType.CIRCLE)
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .size(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.circleicon),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setBrushType(BrushType.RECTANGLE)
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .size(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.rectangleicon),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setColor(Color.Red)
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .size(buttonSize)
                .testTag("redButton")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.redcircle),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setColor(Color.Blue)
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .size(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.bluecircle),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setColor(Color.Green)
            },
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .size(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.greencircle),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
    }
}

@Composable
fun BrushMenuLandscape(viewModel: DrawingViewModel) {
    val buttonSize = 70.dp

    Column (
        Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                viewModel.setBrushType(BrushType.LINE)
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.lineicon),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setBrushType(BrushType.CIRCLE)
            },
            modifier = Modifier
                .width(buttonSize)
                .height(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.circleicon),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
        }
        Button(
            onClick = {
                viewModel.setBrushType(BrushType.RECTANGLE)
            },
            modifier = Modifier
                .width(buttonSize)
                .height(buttonSize)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.rectangleicon),
                    contentDescription = stringResource(id = R.string.android_img_desc),
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
        }
    }
}

@Composable
fun DrawingCanvas(viewModel: DrawingViewModel) {
    val strokes by viewModel.strokes.collectAsState()

    Canvas(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp)
            .border(width = 1.dp, color = Color.Black)
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
        for (stroke in strokes) {
            when (stroke.brushType) {
                BrushType.LINE -> {
                    for (i in 0 until stroke.points.size-1) {
                        drawLine(stroke.color, stroke.points[i], stroke.points[i+1], stroke.size)
                    }
                }
                BrushType.CIRCLE -> {
                    stroke.points.forEach { point ->
                        drawCircle(color=stroke.color, radius=stroke.size, center=point)
                    }
                }
                BrushType.RECTANGLE -> {
                    stroke.points.forEach { point ->
                        drawRect(color=stroke.color, topLeft = Offset(point.x - (stroke.size/2), point.y - (stroke.size/2)), size = Size(stroke.size, stroke.size))
                    }
                }
            }
        }
    }
}