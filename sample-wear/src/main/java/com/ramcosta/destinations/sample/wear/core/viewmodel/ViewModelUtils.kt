package com.ramcosta.destinations.sample.wear.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.ramcosta.destinations.sample.wear.LocalDependencyContainer
import com.ramcosta.destinations.sample.wear.core.di.DependencyContainer

@Composable
inline fun <reified VM : ViewModel> viewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
): VM {
    return androidx.lifecycle.viewmodel.compose.viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = ViewModelFactory(
            dependencyContainer = LocalDependencyContainer.current,
        )
    )
}

@Composable
inline fun <reified VM : ViewModel> activityViewModel(): VM {
    val activity = LocalDependencyContainer.current.activity

    return androidx.lifecycle.viewmodel.compose.viewModel(
        VM::class.java,
        activity,
        null,
        factory = ViewModelFactory(
            dependencyContainer = LocalDependencyContainer.current,
        )
    )
}

class ViewModelFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return dependencyContainer.createViewModel(modelClass) { extras.createSavedStateHandle() }
    }
}