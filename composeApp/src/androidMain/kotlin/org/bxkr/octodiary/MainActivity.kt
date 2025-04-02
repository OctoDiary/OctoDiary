package org.bxkr.octodiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.dark(Color.Black.toArgb())
//        )
        setContent {
            App()
        }
    }
}