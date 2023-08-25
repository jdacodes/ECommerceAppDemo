package com.jdacodes.feca.feature_wishlist.presentation.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdacodes.feca.core.util.UiEvents
import com.jdacodes.feca.feature_wishlist.domain.model.Wishlist
import com.jdacodes.feca.feature_wishlist.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository: WishlistRepository
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    val wishlistItems = repository.getWishlist()

    fun insertFavorite(wishlist: Wishlist) {
        viewModelScope.launch {
            repository.insertToWishlist(wishlist)
        }
    }

    fun inWishlist(id: Int): LiveData<Boolean> {
        return repository.inWishlist(id)
    }

    fun deleteFromWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            repository.deleteOneWishlist(wishlist)
            _eventFlow.emit(
                UiEvents.SnackBarEvent(message = "Item removed from wishlist")
            )
        }
    }

    fun deleteAllWishlist() {
        viewModelScope.launch {
            if (wishlistItems.value.isNullOrEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackBarEvent(message = "No Wishlist items found")
                )
            } else {
                repository.deleteAllWishlist()
                _eventFlow.emit(
                    UiEvents.SnackBarEvent(message = "All items deleted from your wishlist")
                )
            }
        }
    }
}
