package com.example.gameforgasir.data.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UsState(
    val name: String = "",
    val abbreviation: String = "",
    val nickname: String = "",
    @DrawableRes val licensePlate: Int = 0
)
