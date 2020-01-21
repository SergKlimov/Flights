package com.example.flights.presentation.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.flights.presentation.utils.view_model.ViewModelFactoryProvider

fun <T : ViewModel> Fragment.getViewModel(viewModelClass: Class<T>): T {
    return of(this).get(viewModelClass)
}

inline fun <reified T : Any, reified L : LiveData<T>> Fragment.observe(
    liveData: L,
    noinline block: (T) -> Unit
) {
    liveData.observe(this, Observer<T> { it?.let { block(it) } })
}

/**
 * Creates a {@link ViewModelProvider}, which retains ViewModels while a scope of given
 * {@code lifecycleOwner} is alive. More detailed explanation is in {@link ViewModel}.
 * <p>
 * It uses the given {@link Factory} to instantiate new ViewModels.
 *
 * @param lifecycleOwner a lifecycle owner, in whose scope ViewModels should be retained (Fragment, Activity)
 * @param factory  a {@code Factory} to instantiate new ViewModels
 * @return a ViewModelProvider instance
 */
private fun of(
    lifecycleOwner: LifecycleOwner,
    factory: ViewModelProvider.Factory = getViewModelFactory(lifecycleOwner)
): ViewModelProvider = when (lifecycleOwner) {
    is Fragment -> ViewModelProviders.of(lifecycleOwner, factory)
    is FragmentActivity -> ViewModelProviders.of(lifecycleOwner, factory)
    else -> throw IllegalArgumentException("Not supported LifecycleOwner.")
}

/**
 * Returns ViewModelProvider.Factory instance from current lifecycleOwner.
 * Search {@link ViewModelFactoryProvider} are produced according to priorities:
 * 1. Fragment;
 * 2. Parent fragment recursively;
 * 3. Activity;
 * 4. Application.
 */
private fun getViewModelFactory(provider: Any): ViewModelProvider.Factory = when (provider) {
    is ViewModelFactoryProvider -> provider.getViewModelFactory()
    is Fragment -> getViewModelFactory(provider.parentFragment ?: provider.requireActivity())
    is Activity -> getViewModelFactory(provider.application)
    else -> throw IllegalArgumentException("View model factory not found.")
}
