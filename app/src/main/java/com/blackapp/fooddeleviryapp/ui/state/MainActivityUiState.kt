package com.blackapp.fooddeleviryapp.ui.state

import com.blackapp.fooddeleviryapp.ui.model.AppScreens
import com.blackapp.fooddeleviryapp.ui.model.BottomNavigationItems
import com.blackapp.fooddeleviryapp.ui.model.CategoryItemModel
import com.blackapp.fooddeleviryapp.ui.model.DishModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

data class MainActivityUiState(
    val navigationBottomMenuItems: ImmutableList<BottomNavigationItems>,
    val selectedScreen: AppScreens,
    val selectedNavItemIndex: Int,
    val categories: ImmutableList<CategoryItemModel>,
    val dishesList: ImmutableList<DishModel>,
    val dishesFavouriteSets: ImmutableSet<DishModel>,
    val selectedCategory: CategoryItemModel?,
    val selectedDish: DishModel?,
    val isBottomNavigationVisible: Boolean,
    override val loading: Boolean?,
    override val refreshing: Boolean?,
    override val error: String?
) : UiState {
    companion object
}

val MainActivityUiState.Companion.default: MainActivityUiState
    get() = MainActivityUiState(
        navigationBottomMenuItems = emptyList<BottomNavigationItems>().toImmutableList(),
        selectedScreen = AppScreens.Home,
        selectedNavItemIndex = 0,
        categories = emptyList<CategoryItemModel>().toImmutableList(),
        dishesList = emptyList<DishModel>().toImmutableList(),
        dishesFavouriteSets = emptySet<DishModel>().toImmutableSet(),
        selectedCategory = null,
        selectedDish = null,
        isBottomNavigationVisible = true,
        error = null,
        loading = false,
        refreshing = false
    )