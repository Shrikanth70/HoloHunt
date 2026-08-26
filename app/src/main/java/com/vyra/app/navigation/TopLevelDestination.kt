package com.vyra.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.ui.graphics.vector.ImageVector
import com.vyra.app.R

/**
 * The four top-level tabs reachable from the bottom bar. Each entry owns its
 * route, label, and the filled/outlined icon pair (filled = selected).
 */
enum class TopLevelDestination(
    val route: String,
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME("home", R.string.nav_home, Icons.Filled.Home, Icons.Outlined.Home),
    CREATE("create", R.string.nav_create, Icons.Filled.AddCircle, Icons.Outlined.AddCircle),
    LIBRARY("library", R.string.nav_library, Icons.Filled.PhotoLibrary, Icons.Outlined.PhotoLibrary),
    PROFILE("profile", R.string.nav_profile, Icons.Filled.Person, Icons.Outlined.Person),
}
