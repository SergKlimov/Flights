package com.example.flights.presentation.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showSoftInput() {
    this.requestFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
