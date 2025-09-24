package com.example.drawingapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.drawingapp.R

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(), // take up entire screen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_robot_svg),
            contentDescription = stringResource(id = R.string.android_img_desc),
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        )
        Text(
            text = "Android Studio Drawing App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}