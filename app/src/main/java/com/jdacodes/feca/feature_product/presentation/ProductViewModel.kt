package com.jdacodes.feca.feature_product.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.core.util.UiEvents
import com.jdacodes.feca.feature_product.domain.use_case.GetCategoriesUseCase
import com.jdacodes.feca.feature_product.domain.use_case.GetProducts
import com.jdacodes.feca.feature_product.domain.use_case.GetProductsByTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProducts: GetProducts,
    private val getProductsByTitle: GetProductsByTitle,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _selectedCategory = mutableStateOf("All")
    val selectedCategory: State<String> = _selectedCategory

//    private val _categoriesState = mutableStateOf(emptyList<String>())
//    val categoriesState: State<List<String>> = _categoriesState

    private val _categoriesState = mutableStateOf<List<String>>(emptyList())
    val categoriesState: State<List<String>> get() = _categoriesState

    private var productJob: Job? = null
    private var searchJob: Job? = null

    init {
        getCategories()
        getProductsList()
    }

    fun getProductsList(category: String = "All") {
        productJob?.cancel()
        productJob = viewModelScope.launch {
            val searchTerm = searchQuery.value
            delay(500L)
            getProducts()
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (category == "All") {
                                _state.value = state.value.copy(
                                    productItems = if (searchTerm.isEmpty()) {
                                        result.data ?: emptyList()
                                    } else {
                                        result.data?.filter {
                                            it.title.contains(
                                                searchTerm,
                                                ignoreCase = true
                                            )
                                        } ?: emptyList()
                                    },
                                    isLoading = false
                                )
                            } else {
                                _state.value = state.value.copy(
                                    productItems = result.data?.filter { it.category == category }
                                        ?: emptyList(),
                                    isLoading = false
                                )
                            }

                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                productItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UiEvents.SnackBarEvent(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                productItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }


    fun getCategories() {
        viewModelScope.launch {
//            _categoriesState.value = getCategoriesUseCase()
            getCategoriesUseCase.invoke().collect { list ->
                _categoriesState.value = list
            }
        }
    }

    fun setCategory(value: String) {
        _selectedCategory.value = value
    }

    fun setSearchTerm(query: String) {
        _searchQuery.value = query
    }

    fun onSearch(query: String) {
        if (query.isBlank()) {
            getProductsList()
        }
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getProductsByTitle(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                productItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                productItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UiEvents.SnackBarEvent(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                productItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

}