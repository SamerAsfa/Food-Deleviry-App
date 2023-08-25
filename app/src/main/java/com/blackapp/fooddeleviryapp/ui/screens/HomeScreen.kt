package com.blackapp.fooddeleviryapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackapp.fooddeleviryapp.R
import com.blackapp.fooddeleviryapp.ui.compose.ItemPriceView
import com.blackapp.fooddeleviryapp.ui.compose.SearchBar
import com.blackapp.fooddeleviryapp.ui.compose.SearchBarState
import com.blackapp.fooddeleviryapp.ui.model.CategoryItemModel
import com.blackapp.fooddeleviryapp.ui.model.DishModel
import com.blackapp.fooddeleviryapp.utils.screenWidth
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

@Composable
fun HomeScreen(
    modifier: Modifier,
    lazyListState: LazyListState,
    searchBarState: SearchBarState,
    categories: ImmutableList<CategoryItemModel>,
    dishesList: ImmutableList<DishModel>,
    dishesFavouriteList: ImmutableSet<DishModel>,
    selectedCategory: CategoryItemModel?,
    onClearQuery: () -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchSubmission: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onSearchClose: () -> Unit,
    onCategoryItemClick: (CategoryItemModel) -> Unit,
    onDishItemClick: (DishModel) -> Unit,
    onFavouriteClick: (DishModel) -> Unit
) {
    Column(modifier = modifier) {
        HomeHeaderView(
            modifier = Modifier.fillMaxWidth(),
            searchBarState = searchBarState,
            onClearQuery = onClearQuery,
            onQueryChange = onQueryChange,
            onSearchSubmission = onSearchSubmission,
            onSearchFocusChange = onSearchFocusChange,
            onSearchClose = onSearchClose
        )

        Spacer(modifier = Modifier.height(20.dp))

        CategoriesView(
            modifier = Modifier.fillMaxWidth(),
            categories = categories,
            selectedCategory = selectedCategory,
            onItemClick = onCategoryItemClick
        )

        MainCourseView(
            modifier = Modifier.fillMaxWidth(),
            dishesList = dishesList,
            lazyListState = lazyListState,
            dishesFavouriteList = dishesFavouriteList,
            onItemClick = onDishItemClick,
            onFavouriteClick = onFavouriteClick
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeHeaderView(
    modifier: Modifier,
    searchBarState: SearchBarState,
    onClearQuery: () -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchSubmission: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onSearchClose: () -> Unit
) {
    val gradientColor =
        Brush.horizontalGradient(0f to Color(0xFFF99145), 1000f to Color(0xFFF74B70))

    Box(modifier = modifier.background(gradientColor)) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(id = R.drawable.ic_home_banner),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            alpha = 0.20f
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(
                    top = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
                .padding(top = 22.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.delivery),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.delivery_location_title),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Icon(
                            modifier = Modifier
                                .height(screenWidth() * 0.080f)
                                .width(screenWidth() * 0.060f),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    modifier = Modifier
                        .size(screenWidth() * 0.128f)
                        .border(width = 0.dp, color = Color.Transparent, shape = CircleShape),
                    painter = painterResource(id = R.drawable.user_image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(22.dp)),
                textColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.primary,
                onClearQuery = onClearQuery,
                query = searchBarState.query,
                onQueryChange = onQueryChange,
                onSearchFocusChange = onSearchFocusChange,
                onClose = onSearchClose,
                onSearchSubmission = onSearchSubmission
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun CategoriesView(
    modifier: Modifier,
    @StringRes title: Int = R.string.choose_category_title,
    categories: ImmutableList<CategoryItemModel>,
    selectedCategory: CategoryItemModel?,
    onItemClick: (CategoryItemModel) -> Unit
) {
    val state = rememberLazyListState()

    Column(modifier = modifier) {

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = title),
            color = Color(0xFF191A26),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
        ) {
            items(items = categories, key = { it.id }) { item ->
                CategoryItemView(
                    modifier = Modifier.clickable { onItemClick(item) },
                    item = item, isSelected = selectedCategory == item
                )
            }
        }
    }
}

@Composable
fun CategoryItemView(
    modifier: Modifier = Modifier,
    item: CategoryItemModel,
    circleSize: Float = 0.16f,
    iconPadding: Dp = 16.dp,
    borderStroke: Dp = 1.dp,
    isSelected: Boolean
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(screenWidth() * circleSize)
                .then(
                    if (isSelected)
                        Modifier.background(color = item.backgroundColor, shape = CircleShape)
                    else
                        Modifier.border(
                            width = borderStroke,
                            color = item.borderColor,
                            shape = CircleShape
                        )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.padding(iconPadding),
                painter = painterResource(id = item.icon),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.title,
            color = if (isSelected) Color(0xFFFD451C) else Color.Black,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainCourseView(
    modifier: Modifier,
    @StringRes title: Int = R.string.main_course_title,
    @StringRes seeAllTitle: Int = R.string.see_all_title,
    lazyListState: LazyListState,
    dishesList: ImmutableList<DishModel>,
    dishesFavouriteList: ImmutableSet<DishModel>,
    onItemClick: (DishModel) -> Unit,
    onFavouriteClick: (DishModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = title),
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(id = seeAllTitle),
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFFFD451C)
                )
            }
        }

        items(items = dishesList, key = { it.id }) { item ->
            DishItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) },
                item = item,
                isFavourite = dishesFavouriteList.contains(item),
                onFavouriteClick = onFavouriteClick
            )
        }
    }
}

@Composable
private fun DishItem(
    modifier: Modifier = Modifier,
    item: DishModel,
    isFavourite: Boolean,
    onFavouriteClick: (DishModel) -> Unit
) {
    Box(
        modifier = modifier.background(
            color = item.backgroundColor,
            shape = RoundedCornerShape(12.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = item.description,
                color = Color.Black,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {

                    ItemPriceView(modifier = Modifier, price = item.price.toString())

                    Spacer(modifier = Modifier.height(135.dp))

                    Icon(
                        modifier = Modifier
                            .width(screenWidth() * 0.053f)
                            .height(screenWidth() * 0.049f)
                            .clickable { onFavouriteClick(item) },
                        painter = painterResource(id = if (isFavourite) R.drawable.ic_filled_favourite else R.drawable.ic_unfilled_favourite),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Image(
                    modifier = Modifier.size(screenWidth() * 0.53f),
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}