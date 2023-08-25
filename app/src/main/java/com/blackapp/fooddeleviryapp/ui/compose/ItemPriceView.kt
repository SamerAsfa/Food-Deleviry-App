package com.blackapp.fooddeleviryapp.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemPriceView(
    modifier: Modifier,
    iconColor: Color = Color(0xFFFFB950),
    price: String,
    fontSize: Int = 14,
    lineHeight: Int = 18,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Filled.AttachMoney,
            contentDescription = null,
            tint = iconColor
        )

        Text(
            text = price,
            color = Color.Black,
            fontSize = fontSize.sp,
            lineHeight = lineHeight.sp,
            fontWeight = fontWeight
        )
    }
}