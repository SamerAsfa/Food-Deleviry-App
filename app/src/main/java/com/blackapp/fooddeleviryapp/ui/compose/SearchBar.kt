package com.blackapp.fooddeleviryapp.ui.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackapp.fooddeleviryapp.R

@Composable
fun rememberSearchState(): SearchBarState =
    rememberSaveable(saver = SearchBarStateSaver) { SearchBarState() }

class SearchBarState internal constructor(query: String = "", focused: Boolean = false) {
    var query by mutableStateOf(TextFieldValue(query))
    var focused by mutableStateOf(focused)

    fun updateQuery(query: TextFieldValue) {
        this.query = query
    }

    fun updateFocus(focused: Boolean) {
        this.focused = focused
    }

    fun clearQuery() {
        query = TextFieldValue()
    }

    fun removeFocus() {
        focused = false
    }
}

val SearchBarStateSaver = run {
    val queryKey = "Query"
    val focusedKey = "Focused"
    mapSaver(
        save = { mapOf(queryKey to it.query.text, focusedKey to it.focused) },
        restore = { SearchBarState(it[queryKey] as String, it[focusedKey] as Boolean) }
    )
}

@ExperimentalAnimationApi
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: TextFieldValue,
    placeholder: String = stringResource(id = R.string.search_bar_place_holder_hint),
    cursorColor: Color = Color(0xFF808D9E),
    textColor: Color = Color(0xFF808D9E),
    iconColor: Color = Color(0xFF808D9E),
    initialFocused: Boolean = false,
    onSearchSubmission: (String) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onClose: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 2.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = iconColor
            )

            SearchTextField(
                modifier = Modifier
                    .heightIn(min = 40.dp)
                    .weight(1f),
                cursorColor = cursorColor,
                textColor = textColor,
                onSearchSubmission = onSearchSubmission,
                query = query,
                placeholder = placeholder,
                initialFocused = initialFocused,
                onQueryChange = onQueryChange,
                onSearchFocusChange = onSearchFocusChange,
                onClearQuery = onClearQuery
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    query: TextFieldValue,
    placeholder: String,
    cursorColor: Color,
    textColor: Color,
    initialFocused: Boolean = false,
    onSearchSubmission: (String) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit
) {
    val trailingIcon = @Composable {
        IconButton(
            modifier = Modifier
                .alpha(0.33f),
            onClick = {
                onClearQuery()
            }
        ) {
            Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
        }
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (initialFocused)
            focusRequester.requestFocus()
    }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                onSearchFocusChange(it.isFocused)
            },
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchSubmission(query.text)
            },
        ),
        cursorBrush = SolidColor(cursorColor),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp, color = textColor)
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = query.text,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(0.dp, 0.dp, 0.dp, 0.dp),
            trailingIcon = if (query.text.isNotBlank()) trailingIcon else null,
            placeholder = {
                Text(
                    color = Color(0xFF808D9E),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = placeholder,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                errorTextColor = textColor,
                disabledTextColor = textColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = cursorColor,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            )
        )
    }
}