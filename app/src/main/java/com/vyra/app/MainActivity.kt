package com.vyra.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import com.vyra.app.core.designsystem.theme.VyraTheme
import com.vyra.app.ui.VyraApp

/**
 * The single activity that hosts VYRA's Compose UI. Draws edge-to-edge behind
 * the system bars (which the theme renders transparent) for the immersive,
 * full-bleed look the design calls for.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            VyraTheme {
                VyraApp()
            }
        }
    }
}
