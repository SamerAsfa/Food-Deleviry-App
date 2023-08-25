package com.blackapp.fooddeleviryapp.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackapp.fooddeleviryapp.R
import com.blackapp.fooddeleviryapp.utils.screenWidth

@Composable
fun DeliveryTimeView(
    modifier: Modifier,
    @DrawableRes iconRes: Int = R.drawable.ic_scoter,
    time: String,
    durationType: String = "min"
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(screenWidth() * 0.042f),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = "$time $durationType",
                color = Color(0xFF191A26),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}