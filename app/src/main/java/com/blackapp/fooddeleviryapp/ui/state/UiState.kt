package com.blackapp.fooddeleviryapp.ui.state

import androidx.compose.runtime.Immutable

@Immutable
interface UiState {
    val loading: Boolean?
    val refreshing: Boolean?
    val error: String?
}