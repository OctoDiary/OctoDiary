package org.bxkr.octodiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.bxkr.octodiary.di.getDeeplinkHolder
import org.bxkr.octodiary.ui.screen.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        getDeeplinkHolder().updateDeeplink(intent.data?.toString())
        enableEdgeToEdge()

        setContent {
            App()
        }
    }
}