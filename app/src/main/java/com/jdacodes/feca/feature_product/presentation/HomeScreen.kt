package com.jdacodes.feca.feature_product.presentation


import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jdacodes.feca.core.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: ProductViewModel = hiltViewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val categories = viewModel.categoriesState.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is UiEvents.NavigateEvent -> {

                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,

        ) { paddingValues ->
        paddingValues

        ProductListElement(
            productItems = state.productItems,
            navigator = navigator,
            isLoading = state.isLoading,
            viewModel = viewModel,
            categories = categories,
            selectedCategory = viewModel.selectedCategory.value,
            onSelectCategory = { category ->
                viewModel.setCategory(category)
                viewModel.getProductsList(viewModel.selectedCategory.value)
            }
        )


    }
}
