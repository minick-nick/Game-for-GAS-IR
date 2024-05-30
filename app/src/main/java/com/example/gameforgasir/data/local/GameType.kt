package com.example.gameforgasir.data.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.gameforgasir.ui.navigation.NavigationDestination

data class GameType(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val destinationScreen: NavigationDestination
)
