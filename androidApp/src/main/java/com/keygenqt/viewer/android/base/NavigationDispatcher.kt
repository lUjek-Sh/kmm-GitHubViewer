/*
 * Copyright 2021 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.viewer.android.base

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Navigation dispatcher for routing
 */
@OptIn(ExperimentalPagerApi::class)
class NavigationDispatcher(
    private val scope: CoroutineScope,
    private val controller: NavHostController,
    private val lifecycleOwner: LifecycleOwner,
    private val backPressedDispatcher: OnBackPressedDispatcher
) {
    /**
     * Save start destination
     */
    private var _startDestination: NavDestination? = null

    /**
     * Save count open start destination
     */
    private var _firstDestinationCount: Int = 0

    /**
     * Save current destination
     */
    private var _currentDestination: NavDestination? = null

    /**
     * Navigation [PagerState]
     */
    private var _pager: PagerState? = null

    /**
     * State navigation [PagerState]
     */
    private var _pagerEnable: Boolean = true

    /**
     * Get start destination
     */
    val startDestination get() = _startDestination

    /**
     * Get current destination
     */
    val currentDestination get() = _currentDestination

    /**
     * @todo fix problem lifecycleOwner
     * Custom navigator callback
     */
    private val navigatorCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    init {
        // listen change destination
        val callback = NavController.OnDestinationChangedListener { controller, _, _ ->
            controller.currentDestination?.let { destination ->
                // clear pager
                _pager = null
                // add start destination
                if (_startDestination == null) {
                    _startDestination = destination
                }
                // change counter open start destination
                if (_startDestination?.route == destination.route) {
                    _firstDestinationCount += 1
                }
                // save current destination
                _currentDestination = destination
            }
        }
        // add listener
        controller.addOnDestinationChangedListener(callback)
        // add custom callback
        backPressedDispatcher.addCallback(lifecycleOwner, navigatorCallback)
    }

    /**
     * Check is navigation stack empty
     */
    fun hasEnabledCallbacks(): Boolean {
        // check pager
        if (_pager != null && _pagerEnable && _pager!!.currentPage > 0) {
            return true
        }
        // check root destination
        if (_startDestination?.route == currentDestination?.route && _firstDestinationCount == 1) {
            return false
        }
        // check BackPressedDispatcher
        return backPressedDispatcher.hasEnabledCallbacks()
    }

    /**
     * Step to back on navigation or pager
     */
    fun onBackPressed() {
        _pager?.let {
            if (!it.isScrollInProgress) {
                if (it.currentPage > 0 && _pagerEnable) {
                    scope.launch {
                        it.animateScrollToPage(it.currentPage - 1)
                    }
                } else {
                    backPressed()
                }
            }
        } ?: run {
            backPressed()
        }
    }

    /**
     * Press back [OnBackPressedDispatcher] with counter
     */
    private fun backPressed() {
        // change counter close start destination
        if (_startDestination?.route == currentDestination?.route) {
            _firstDestinationCount -= 1
        }
        // disable callback
        navigatorCallback.remove()
        // onBackPressed
        backPressedDispatcher.onBackPressed()
        // enable callback
        backPressedDispatcher.addCallback(lifecycleOwner, navigatorCallback)
    }

    /**
     * Clear stack and open screens
     */
    fun toRoutePopStack(vararg routes: () -> Unit) {
        if (routes.isNotEmpty()) {
            // clear stack
            _startDestination?.let { des ->
                (0.._firstDestinationCount).forEach { _ ->
                    controller.popBackStack(des.id, true)
                }
            }
            // clear first destination
            _startDestination = null
            // emit routes
            routes.forEach {
                it.invoke()
            }
        }
    }

    /**
     * Set pager [PagerState] and callback change
     */
    fun setPager(state: PagerState, change: (Int) -> Unit = {}) {
        if (_pager == null) {
            _pager = state
            scope.launch {
                snapshotFlow { state.currentPage }.collect {
                    change.invoke(it)
                }
            }
        }
    }

    /**
     * Enable navigation pager
     */
    fun enablePager() {
        _pagerEnable = true
    }

    /**
     * Disable navigation pager
     */
    fun disablePager() {
        _pagerEnable = false
    }
}
