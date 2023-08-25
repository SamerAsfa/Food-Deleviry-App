package com.blackapp.fooddeleviryapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blackapp.fooddeleviryapp.ui.compose.rememberSearchState
import com.blackapp.fooddeleviryapp.ui.model.AppScreens
import com.blackapp.fooddeleviryapp.ui.model.BottomNavigationItems
import com.blackapp.fooddeleviryapp.ui.screens.DishDetailsScreen
import com.blackapp.fooddeleviryapp.ui.screens.FavouriteScreen
import com.blackapp.fooddeleviryapp.ui.screens.HomeScreen
import com.blackapp.fooddeleviryapp.ui.state.MainActivityUiState
import com.blackapp.fooddeleviryapp.ui.theme.FoodDeleviryAppTheme
import com.blackapp.fooddeleviryapp.utils.screenWidth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableList

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            FoodDeleviryAppTheme(
                darkTheme = false,
                statusBarColor = Color.Transparent,
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    override fun onBackPressed() {
        val selectedScreen = viewModel.uiState.value.selectedScreen
        if (selectedScreen == AppScreens.Home)
            super.onBackPressed()
        else
            viewModel.handelOnBackPressed()
    }

    @Composable
    private fun MainScreen(modifier: Modifier) {
        val uiState: MainActivityUiState by viewModel.uiState.collectAsStateWithLifecycle()

        Scaffold(
            modifier = modifier,
            bottomBar = {
                AnimatedVisibility(visible = uiState.isBottomNavigationVisible) {
                    BottomNavigationView(
                        uiState = uiState,
                        selectedItemIndex = uiState.selectedNavItemIndex,
                        onItemClick = { item, index ->
                            viewModel.handelBottomNavigationClick(index, item)
                        })
                }
            }
        ) { contentPadding ->

            val searchState = rememberSearchState()
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            val mainScreenLazyListState = rememberLazyListState()
            val favouriteScreenLazyListState = rememberLazyListState()

            Crossfade(
                targetState = uiState.selectedScreen,
                label = uiState.selectedScreen.name
            ) { screen ->
                when (screen) {
                    AppScreens.Home -> HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = contentPadding.calculateBottomPadding()
                            ),
                        lazyListState = mainScreenLazyListState,
                        searchBarState = searchState,
                        categories = uiState.categories,
                        dishesList = uiState.dishesList,
                        dishesFavouriteList = uiState.dishesFavouriteSets,
                        selectedCategory = uiState.selectedCategory,
                        onClearQuery = {
                            searchState.query = TextFieldValue("")
                        },
                        onQueryChange = { query ->
                            searchState.query = query
                        },
                        onSearchSubmission = { query ->
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        },
                        onSearchFocusChange = { focused ->
                            searchState.focused = focused
                        },
                        onSearchClose = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            searchState.query = TextFieldValue("")
                        },
                        onCategoryItemClick = viewModel::handleOnCategoryItemClick,
                        onDishItemClick = viewModel::handleOnDishItemClick,
                        onFavouriteClick = viewModel::handleDishFavouriteClick
                    )

                    AppScreens.Details -> {

                        val model = uiState.selectedDish
                        DishDetailsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = contentPadding.calculateTopPadding())
                                .verticalScroll(rememberScrollState()),
                            item = model,
                            isFavourite = uiState.dishesFavouriteSets.contains(model),
                            onFavouriteClick = { viewModel.handleDishFavouriteClick(model) },
                            onBackClick = viewModel::handelOnBackPressed
                        )
                    }

                    AppScreens.Favourite -> {
                        val favouriteDishesList =
                            uiState.dishesFavouriteSets.toImmutableList()

                        FavouriteScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                                .padding(top = contentPadding.calculateTopPadding()),
                            lazyListState = favouriteScreenLazyListState,
                            favouriteList = favouriteDishesList,
                            onSearchClick = {},
                            onUnFavouriteClick = viewModel::unFavouriteDish
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    @Composable
    private fun BottomNavigationView(
        uiState: MainActivityUiState,
        selectedItemIndex: Int,
        onItemClick: (BottomNavigationItems, Int) -> Unit
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White
        ) {
            uiState.navigationBottomMenuItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.White),
                    onClick = { onItemClick(item, index) },
                    icon = {
                        Icon(
                            modifier = Modifier
                                .then(
                                    if (item == BottomNavigationItems.Cart)
                                        Modifier.size(screenWidth() * 0.128f)
                                    else
                                        Modifier.size(screenWidth() * 0.064f)
                                ),
                            painter = painterResource(id = if (selectedItemIndex == index) item.selectedIcon else item.unSelectedIcon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }
    }
}