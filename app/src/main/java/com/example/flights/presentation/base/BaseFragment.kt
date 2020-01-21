package com.example.flights.presentation.base

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.flights.R
import com.google.android.material.snackbar.Snackbar

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected fun showError(@StringRes descriptionStringRes: Int) {
        Snackbar.make(requireView(), descriptionStringRes, Snackbar.LENGTH_SHORT).show()
    }

    protected open fun onErrorEvent(throwable: Throwable) {
        showCommonError()
    }

    private fun showCommonError() {
        showError(R.string.global_error_something_went_wrong)
    }

}
