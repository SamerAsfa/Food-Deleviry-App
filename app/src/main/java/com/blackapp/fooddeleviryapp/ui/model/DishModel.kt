package com.blackapp.fooddeleviryapp.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class DishModel(
    val id:Int,
    @DrawableRes val image: Int,
    val description: String,
    val backgroundColor: Color,
    val price: Double
)