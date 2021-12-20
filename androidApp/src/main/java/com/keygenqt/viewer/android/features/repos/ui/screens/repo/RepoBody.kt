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
package com.keygenqt.viewer.android.features.repos.ui.screens.repo

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.keygenqt.viewer.android.R
import com.keygenqt.viewer.android.base.viewModel.queryActions.QueryState
import com.keygenqt.viewer.android.compose.components.AppScaffold
import com.keygenqt.viewer.android.data.models.RepoModel
import com.keygenqt.viewer.android.features.repos.ui.actions.RepoActions

/**
 * Body for [RepoScreen]
 */
@Composable
fun RepoBody(
    model: Any?,
    state1: QueryState = QueryState.Start,
    onActions: (RepoActions) -> Unit = {},
) {
    val lazyListState = rememberLazyListState()

    // state query 1
    var loadingRepo by remember { mutableStateOf(false) }

    RepoQueryState1(
        state = state1,
        loadingRepo = {
            loadingRepo = true
        },
        clear = {
            loadingRepo = false
        }
    )

    AppScaffold(
        topBarLoading = model == false,
        topBarTitle = stringResource(id = R.string.repo_title),
        lazyListState = lazyListState,
        swipeRefreshEnable = true,
        swipeRefreshLoading = loadingRepo,
        swipeRefreshAction = {
            onActions(RepoActions.ActionUpdateRepo)
        },
    ) {
        when (model) {
            is RepoModel -> RepoInfo(
                model = model,
                lazyListState = lazyListState
            )
        }
    }
}
