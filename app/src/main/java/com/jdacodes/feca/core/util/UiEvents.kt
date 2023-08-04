package com.jdacodes.feca.core.util

sealed class UiEvents {
    data class SnackBarEvent(val message: String) : UiEvents()
    data class NavigateEvent(val route: String) : UiEvents()
}
