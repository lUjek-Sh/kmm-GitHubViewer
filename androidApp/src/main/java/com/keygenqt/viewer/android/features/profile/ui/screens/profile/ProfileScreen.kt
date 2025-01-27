/*
 * Copyright 2022 Vitaliy Zarubin
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
package com.keygenqt.viewer.android.features.profile.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import com.keygenqt.viewer.android.base.LocalNavigationDispatcher
import com.keygenqt.viewer.android.features.profile.ui.actions.ProfileActions
import com.keygenqt.viewer.android.features.profile.ui.viewModels.ProfileViewModel

/**
 * Base page fun for initialization
 *
 * @param viewModel page view model
 * @param onActions actions for page
 */
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onActions: (ProfileActions) -> Unit = {},
) {
    val model by viewModel.user.collectAsState(false)
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    ProfileBody(
        model = model,
        error = error,
        loading = loading,
        onActions = onActions,
        uriHandler = LocalUriHandler.current,
        navDispatcher = LocalNavigationDispatcher.current
    )
}
