package com.blackapp.fooddeleviryapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackapp.fooddeleviryapp.R
import com.blackapp.fooddeleviryapp.ui.compose.DeliveryTimeView
import com.blackapp.fooddeleviryapp.ui.compose.DishRateView
import com.blackapp.fooddeleviryapp.ui.compose.OrderCountView
import com.blackapp.fooddeleviryapp.ui.model.DishModel
import com.blackapp.fooddeleviryapp.utils.screenWidth

@Composable
fun DishDetailsScreen(
    modifier: Modifier,
    item: DishModel?,
    isFavourite: Boolean,
    onFavouriteClick: (DishModel) -> Unit,
    onBackClick: () -> Unit
) {
    var itemCount by remember {
        mutableIntStateOf(1)
    }
    var totalPrice by remember {
        mutableStateOf(item?.price)
    }

    val gradientColor =
        Brush.horizontalGradient(0f to Color(0xFFF99145), 1000f to Color(0xFFF74B70))

    Column(modifier = modifier.background(gradientColor)) {

        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .size(screenWidth() * 0.064f)
                        .clickable(onClick = onBackClick),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = Color(0xFF191A26)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .size(screenWidth() * 0.064f)
                        .clickable(
                            onClick = {
                                if (item != null) {
                                    onFavouriteClick(item)
                                }
                            }),
                    painter = painterResource(id = if (isFavourite) R.drawable.ic_filled_favourite else R.drawable.ic_unfilled_favourite),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            item?.description?.let { description ->
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = description,
                    color = Color.Black,
                    fontSize = 24.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "By Resto Parmato Bapo",
                color = Color(0xFF8D93A1),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxWidth()) {

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth(0.5f)
                        .padding(start = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    DishRateView(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color(0xFFEAEDF3),
                            shape = RoundedCornerShape(20.dp)
                        ),
                        rate = "4,9"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    DeliveryTimeView(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color(0xFFEAEDF3),
                            shape = RoundedCornerShape(20.dp)
                        ),
                        time = "20"
                    )

                    Spacer(modifier = Modifier.height(58.dp))

                    OrderCountView(
                        modifier = Modifier.background(
                            color = Color(0xFF282A33),
                            shape = CircleShape
                        ), count = itemCount,
                        onCountChange = {
                            itemCount = itemCount.plus(1)
                            totalPrice = item?.price?.times(itemCount)
                        })
                }

                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (50).dp)
                        .width(screenWidth() * 0.653f)
                        .height(screenWidth() * 0.853f),
                    painter = painterResource(id = R.drawable.dish_details),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.description_title),
                color = Color(0xFF191A26),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF8D93A1),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consat du veniam consequat coseqtures adipsing content...")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFFFB950),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append("Read more")
                }
            }

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = text
            )

            Spacer(modifier = Modifier.height(50.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        FooterView(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
            itemCount = itemCount,
            totalPrice =totalPrice?: 0.0,
            onCartClick = {})
    }
}

@Composable
private fun FooterView(
    modifier: Modifier,
    itemCount: Int,
    totalPrice: Double,
    onCartClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$itemCount Items",
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Icon(
                modifier = Modifier
                    .width(screenWidth() * 0.035f)
                    .height(screenWidth() * 0.045f),
                imageVector = Icons.Filled.AttachMoney,
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = totalPrice.toString().substring(0,4),
                color = Color.White,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Icon(
            modifier = Modifier
                .size(screenWidth() * 0.149f)
                .clickable(onClick = onCartClick),
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}