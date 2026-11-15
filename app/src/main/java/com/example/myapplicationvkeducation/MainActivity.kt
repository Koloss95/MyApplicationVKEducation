package com.example.myapplicationvkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplicationvkeducation.presentation.navigation.RootNavHost
import com.example.myapplicationvkeducation.presentation.theme.MyApplicationVKEducationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationVKEducationTheme {
                RootNavHost()
            }
        }
    }
}
