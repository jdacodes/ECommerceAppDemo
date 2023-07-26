package com.jdacodes.feca.feature_product.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = paddingLeadingIconEnd,
                    end = paddingTrailingIconStart
                )
        ) {
            TextField(
                singleLine = true,
                value = viewModel.searchQuery.value,
                onValueChange = viewModel::onSearch,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search ...")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                    selectionColors = TextSelectionColors(
                        handleColor = MaterialTheme.colorScheme.background,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
                ),
                shape = CircleShape,
                leadingIcon = leadingIcon
            )

        }


    }


}