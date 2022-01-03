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
package com.keygenqt.viewer.android.features.profile.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.keygenqt.viewer.android.base.queryActions.QueryActionsState
import com.keygenqt.viewer.android.base.queryActions.QueryState

@Composable
fun SettingsQueryState1(
    state: QueryState = QueryState.Start,
    loading: () -> Unit = {},
    success: () -> Unit = {},
    error: (String?) -> Unit = {},
    clear: () -> Unit = {}
) {
    QueryActionsState(state) {
        clear.invoke()
        when (this) {
            is QueryState.Action -> loading.invoke()
            is QueryState.Success<*> -> success.invoke()
            is QueryState.Error -> error.invoke(stringResource(id = exception.resId))
            else -> {}
        }
    }
}
