package com.example.drawingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.drawingapp.Navigation.AppNavHost
import com.example.drawingapp.ui.theme.DrawingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawingAppTheme {
                val myNavControl = rememberNavController()
                AppNavHost(myNavControl)
            }
        }
    }
}
