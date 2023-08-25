package com.blackapp.fooddeleviryapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackapp.fooddeleviryapp.R
import com.blackapp.fooddeleviryapp.ui.compose.ItemPriceView
import com.blackapp.fooddeleviryapp.ui.model.DishModel
import com.blackapp.fooddeleviryapp.ui.model.FavouriteSwitcherType
import com.blackapp.fooddeleviryapp.utils.screenWidth
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FavouriteScreen(
    modifier: Modifier,
    lazyListState:LazyListState,
    favouriteList: ImmutableList<DishModel>,
    onSearchClick: () -> Unit,
    onUnFavouriteClick: (DishModel) -> Unit
) {
    var selectedType by remember {
        mutableStateOf(FavouriteSwitcherType.Favourite)
    }

    Column(modifier = modifier) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = stringResource(id = R.string.favourite_title),
                color = Color(0xFF191A26),
                fontSize = 32.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier.size(screenWidth() * 0.055f),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        FavouriteSwitcherView(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .background(
                    color = Color(0xFFF6F7FA),
                    shape = RoundedCornerShape(40.dp)
                ),
            selectedType = selectedType,
            onSwitchTypeClick = {
                selectedType = when (selectedType) {
                    FavouriteSwitcherType.Favourite -> FavouriteSwitcherType.MyCourse
                    FavouriteSwitcherType.MyCourse -> FavouriteSwitcherType.Favourite
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        FavouriteList(
            modifier = Modifier.fillMaxWidth(),
            lazyListState=lazyListState,
            favouriteList = favouriteList,
            onUnFavouriteClick = onUnFavouriteClick
        )
    }
}

@Composable
private fun FavouriteSwitcherView(
    modifier: Modifier,
    selectedType: FavouriteSwitcherType,
    onSwitchTypeClick: (FavouriteSwitcherType) -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF242B42),
                    containerColor = if (selectedType == FavouriteSwitcherType.Favourite) Color.White else Color.Transparent
                ),
                onClick = {
                    if (selectedType != FavouriteSwitcherType.Favourite) onSwitchTypeClick(
                        FavouriteSwitcherType.Favourite
                    )
                }) {
                Text(
                    text = stringResource(id = R.string.button_favourite_title),
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF242B42),
                    containerColor = if (selectedType == FavouriteSwitcherType.MyCourse) Color.White else Color.Transparent
                ),
                onClick = {
                    if (selectedType != FavouriteSwitcherType.MyCourse) onSwitchTypeClick(
                        FavouriteSwitcherType.MyCourse
                    )
                }) {
                Text(
                    text = stringResource(id = R.string.button_my_order_title),
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun FavouriteList(
    modifier: Modifier,
    lazyListState:LazyListState,
    favouriteList: ImmutableList<DishModel>,
    onUnFavouriteClick: (DishModel) -> Unit
) {
      LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(favouriteList) { index, item ->
            FavouriteItemView(
                modifier = Modifier.fillMaxWidth(),
                item = item,
                onUnFavouriteClick = onUnFavouriteClick
            )

            if (index < favouriteList.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))

                Divider(thickness = 0.5.dp)
            }
        }
    }
}

@Composable
private fun FavouriteItemView(
    modifier: Modifier,
    item: DishModel,
    onUnFavouriteClick: (DishModel) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Image(
            modifier = Modifier
                .size(screenWidth() * 0.213f)
                .background(color = Color.Unspecified, shape = RoundedCornerShape(12.dp)),
            painter = painterResource(id = item.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = item.description,
                color = Color(0xFF191A26),
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            ItemPriceView(
                modifier = Modifier,
                price = item.price.toString(),
                iconColor = Color(0xFF191A26),
                fontSize = 12,
                lineHeight = 18,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "by Eco Kitchen",
                    color = Color(0xFF7E8CA0),
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    modifier = Modifier.size(screenWidth() * 0.042f),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = item.price.toString(),
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFF242B42),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .size(screenWidth() * 0.064f)
                        .clickable { onUnFavouriteClick(item) },
                    painter = painterResource(id = R.drawable.ic_filled_favourite),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}
