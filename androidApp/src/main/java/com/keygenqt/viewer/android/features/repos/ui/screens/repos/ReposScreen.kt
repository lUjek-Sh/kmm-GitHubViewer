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
package com.keygenqt.viewer.android.features.repos.ui.screens.repos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.keygenqt.viewer.android.base.LocalNavigationDispatcher
import com.keygenqt.viewer.android.data.models.RepoModel
import com.keygenqt.viewer.android.features.repos.ui.actions.ReposActions
import com.keygenqt.viewer.android.features.repos.ui.viewModels.ReposViewModel

/**
 * Base page fun for initialization
 *
 * @param viewModel page view model
 * @param onActions actions for page
 */
@Composable
fun ReposScreen(
    viewModel: ReposViewModel,
    onActions: (ReposActions) -> Unit = {},
) {

    val lazyRepos: LazyPagingItems<RepoModel> = viewModel.listRepo.collectAsLazyPagingItems()
    val isSortASCListRepo: Boolean by viewModel.isSortASCListRepo.collectAsState()

    ReposBody(
        isSortASCListRepo = isSortASCListRepo,
        models = lazyRepos,
        onActions = onActions,
        navDispatcher = LocalNavigationDispatcher.current
    )
}
