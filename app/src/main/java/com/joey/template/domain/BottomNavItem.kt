package com.joey.template.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String, // 标题
    val icon: ImageVector // 图标资源
) {
    object Home : BottomNavItem("Home", Icons.Default.Home)
    object Search : BottomNavItem("Search", Icons.Default.Search)
}

