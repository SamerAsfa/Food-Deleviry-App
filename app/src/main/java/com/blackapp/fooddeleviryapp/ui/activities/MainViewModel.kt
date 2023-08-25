package com.blackapp.fooddeleviryapp.ui.activities

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.blackapp.fooddeleviryapp.R
import com.blackapp.fooddeleviryapp.ui.model.AppScreens
import com.blackapp.fooddeleviryapp.ui.model.BottomNavigationItems
import com.blackapp.fooddeleviryapp.ui.model.CategoryItemModel
import com.blackapp.fooddeleviryapp.ui.model.DishModel
import com.blackapp.fooddeleviryapp.ui.state.MainActivityUiState
import com.blackapp.fooddeleviryapp.ui.state.default
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainActivityUiState.default)
    val uiState = _uiState.asStateFlow()

    init {
        prepareNavigationBottomMenu()
        preparingCategoryItems()
        preparingDishesItems()
    }

    private fun isBottomNavigationShowing(screen: AppScreens): Boolean {
        return when (screen) {
            AppScreens.Details -> false
            else -> true
        }
    }

    private fun prepareNavigationBottomMenu() {
        val bottomMenuItemsList = mutableListOf<BottomNavigationItems>()

        bottomMenuItemsList.add(
            BottomNavigationItems.Home
        )
        bottomMenuItemsList.add(
            BottomNavigationItems.Favourite
        )
        bottomMenuItemsList.add(
            BottomNavigationItems.Cart
        )
        bottomMenuItemsList.add(
            BottomNavigationItems.Wishlist
        )
        bottomMenuItemsList.add(
            BottomNavigationItems.Profile
        )

        _uiState.update {
            it.copy(
                navigationBottomMenuItems = bottomMenuItemsList.toImmutableList()
            )
        }
    }

    private fun preparingCategoryItems() {
        val categories = listOf<CategoryItemModel>(
            CategoryItemModel(
                id = 1,
                title = "All",
                icon = R.drawable.ic_category_all,
                backgroundColor = Color(0xFFFFEEE8),
                borderColor = Color(0xFFFFEEE8)
            ),
            CategoryItemModel(
                id = 2,
                title = "Main",
                icon = R.drawable.ic_category_main,
                backgroundColor = Color(0xFFFFEEE8),
                borderColor = Color(0xFFFFEEE8)
            ),
            CategoryItemModel(
                id = 3,
                title = "Dessert",
                icon = R.drawable.ic_category_dessert,
                backgroundColor = Color(0xFFFFEEE8),
                borderColor = Color(0xFFFFEEE8)
            ),
            CategoryItemModel(
                id = 4,
                title = "Drinks",
                icon = R.drawable.ic_category_drinks,
                backgroundColor = Color(0xFFFFEEE8),
                borderColor = Color(0xFFFFEEE8)
            )
        )

        _uiState.update {
            it.copy(
                categories = categories.toImmutableList(),
                selectedCategory = categories.getOrNull(1)
            )
        }
    }

    private fun preparingDishesItems() {
        val dishesList = listOf<DishModel>(
            DishModel(
                id = 1,
                image = R.drawable.dish_one,
                description = "Healthy Salad for Lunch",
                backgroundColor = Color(0xFFEBF8E6),
                price = 9.67
            ),
            DishModel(
                id = 2,
                image = R.drawable.dish_tow,
                description = "Grilled Beaf Steak with Sauce ABC",
                backgroundColor = Color(0xFFE8F1FF),
                price = 9.67
            ),
            DishModel(
                id = 3,
                image = R.drawable.dish_one,
                description = "Healthy Salad for Lunch",
                backgroundColor = Color(0xFFEBF8E6),
                price = 10.7
            ),

            DishModel(
                id = 4,
                image = R.drawable.dish_tow,
                description = "Grilled Beaf Steak with Sauce ABC",
                backgroundColor = Color(0xFFE8F1FF),
                price = 11.3
            )
        )

        _uiState.update {
            it.copy(
                dishesList = dishesList.toImmutableList()
            )
        }
    }

    fun handelBottomNavigationClick(index: Int, screen: BottomNavigationItems) {
        val _screen = when (screen) {
            BottomNavigationItems.Cart -> AppScreens.Cart
            BottomNavigationItems.Favourite -> AppScreens.Favourite
            BottomNavigationItems.Home -> AppScreens.Home
            BottomNavigationItems.Profile -> AppScreens.Profile
            BottomNavigationItems.Wishlist -> AppScreens.Wishlist
        }

        _uiState.update {
            it.copy(
                selectedScreen = _screen,
                selectedNavItemIndex = index,
                isBottomNavigationVisible = isBottomNavigationShowing(_screen)
            )
        }
    }

    fun handleOnDishItemClick(item: DishModel) {
        _uiState.update {
            it.copy(
                selectedScreen = AppScreens.Details,
                selectedDish = item,
                isBottomNavigationVisible = isBottomNavigationShowing(AppScreens.Details)
            )
        }
    }

    fun handleDishFavouriteClick(item: DishModel?) {
        if (item == null) return

        val dishesFavouriteIndexes = uiState.value.dishesFavouriteSets.toMutableSet()

        if (dishesFavouriteIndexes.contains(item))
            dishesFavouriteIndexes.remove(item)
        else
            dishesFavouriteIndexes.add(item)

        _uiState.update {
            it.copy(
                dishesFavouriteSets = dishesFavouriteIndexes.toImmutableSet(),
            )
        }
    }

    fun handelOnBackPressed() {
        _uiState.update {
            it.copy(
                selectedScreen = AppScreens.Home,
                selectedNavItemIndex = 0,
                selectedDish = null,
                isBottomNavigationVisible = isBottomNavigationShowing(AppScreens.Home)
            )
        }
    }

    fun handleOnCategoryItemClick(category: CategoryItemModel) {
        _uiState.update {
            it.copy(
                selectedCategory = category
            )
        }
    }

    fun unFavouriteDish(it: DishModel) {
        val dishesFavouriteList = uiState.value.dishesFavouriteSets.toMutableSet()
        dishesFavouriteList.remove(it)

        _uiState.update {
            it.copy(
                dishesFavouriteSets = dishesFavouriteList.toImmutableSet()
            )
        }
    }
}