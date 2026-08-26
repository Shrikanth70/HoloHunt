package com.vyra.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point. [HiltAndroidApp] triggers Hilt's code generation and
 * creates the app-level dependency container that all injected components use.
 */
@HiltAndroidApp
class VyraApplication : Application()
