package com.example.fitnessapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @DrawableRes val drawableRes: Int,
    @StringRes val stringRes: Int,
    val color: Int
)
