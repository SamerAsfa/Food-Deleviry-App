package com.blackapp.fooddeleviryapp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import com.blackapp.fooddeleviryapp.utils.screenWidth

@Composable
fun OrderCountView(
    modifier: Modifier=Modifier,
    count: Int,
    onCountChange: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(screenWidth() * 0.133f)
                .padding(2.dp),
            onClick = { onCountChange(count.plus(1)) },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF363944),
                contentColor = Color.White
            )
        ) {
            Icon(
                modifier = Modifier.size(screenWidth() * 0.042f),
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(text = count.toString(), color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))
    }
}