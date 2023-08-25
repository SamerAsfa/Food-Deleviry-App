package com.blackapp.fooddeleviryapp.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.blackapp.fooddeleviryapp.R

@Immutable
sealed class BottomNavigationItems(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
) {
    data object Home : BottomNavigationItems(
        route = "Home",
        resourceId = R.string.menu_home,
        selectedIcon = R.drawable.ic_selected_home,
        unSelectedIcon = R.drawable.ic_unselected_home
    )

    data object Favourite : BottomNavigationItems(
        route = "Favourite",
        resourceId = R.string.menu_favourite,
        selectedIcon = R.drawable.ic_filled_favourite,
        unSelectedIcon = R.drawable.ic_favourite
    )

    data object Cart : BottomNavigationItems(
        route = "Cart",
        resourceId = R.string.menu_cart,
        selectedIcon = R.drawable.ic_cart,
        unSelectedIcon = R.drawable.ic_cart
    )

    data object Wishlist : BottomNavigationItems(
        route = "Wishlist",
        resourceId = R.string.menu_wishlist,
        selectedIcon = R.drawable.ic_filled_wishlist,
        unSelectedIcon = R.drawable.ic_wishlist
    )

    data object Profile : BottomNavigationItems(
        route = "Profile",
        resourceId = R.string.menu_wishlist,
        selectedIcon = R.drawable.ic_filled_profile,
        unSelectedIcon = R.drawable.ic_profile
    )
}