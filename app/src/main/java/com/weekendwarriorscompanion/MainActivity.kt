package com.weekendwarriorscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.weekendwarriorscompanion.ui.theme.WeekendWarriorsCompanionTheme
import com.weekendwarriorscompanion.ui.MainApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeekendWarriorsCompanionTheme {
                MainApp(this)
            }
        }
    }
}
