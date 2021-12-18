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
package com.keygenqt.viewer.android.features.other.ui.screens.startPage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.keygenqt.viewer.android.base.LocalViewModel
import com.keygenqt.viewer.android.features.other.ui.actions.StartPageActions
import com.keygenqt.viewer.android.features.other.ui.viewModels.StartPageViewModel

/**
 * Start page for loading required data
 *
 * @param viewModel page view model
 * @param onActions actions for page
 */
@Composable
fun StartPageScreen(
    viewModel: StartPageViewModel,
    onActions: (StartPageActions) -> Unit = {}
) {

    val state1 by viewModel.query1.state.collectAsState()
    val isReady: Boolean by LocalViewModel.current.isSplash.collectAsState()

    LaunchedEffect(isReady) {
        if (isReady) {
            onActions(StartPageActions.StartLoadingData)
        }
    }

    StartPageBody(
        state1 = state1,
        onActions = onActions,
    )
}
