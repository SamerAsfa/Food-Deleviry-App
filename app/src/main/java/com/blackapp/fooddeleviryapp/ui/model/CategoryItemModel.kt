package com.blackapp.fooddeleviryapp.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CategoryItemModel(
    val id:Int,
    val title: String,
    @DrawableRes val icon: Int,
    val backgroundColor: Color,
    val borderColor: Color
)