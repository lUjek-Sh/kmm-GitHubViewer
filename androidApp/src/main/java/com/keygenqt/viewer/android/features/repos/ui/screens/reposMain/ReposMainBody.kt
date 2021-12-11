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
package com.keygenqt.viewer.android.features.repos.ui.screens.reposMain

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.keygenqt.viewer.android.R
import com.keygenqt.viewer.android.compose.components.AppScaffold
import com.keygenqt.viewer.android.compose.components.AppSwipeRefreshList
import com.keygenqt.viewer.android.compose.components.RotateIconSort
import com.keygenqt.viewer.android.data.models.RepoModel
import com.keygenqt.viewer.android.features.repos.ui.actions.ReposMainActions
import com.keygenqt.viewer.android.theme.AppTheme

@Composable
fun ReposMainBody(
    isSortDescListRepo: Boolean = false,
    models: LazyPagingItems<RepoModel>,
    onActions: (ReposMainActions) -> Unit = {},
) {
    val refreshState = rememberSwipeRefreshState(models.loadState.refresh is LoadState.Loading)

    AppScaffold(
        refreshState = refreshState,
        title = stringResource(id = R.string.repos_title),
        actions = {
            RotateIconSort(isRotate = isSortDescListRepo) {
                onActions(ReposMainActions.SortToggle)
                models.refresh()
            }
        }
    ) {
        AppSwipeRefreshList(
            refreshState = refreshState,
            items = models,
        ) { index, item ->
            ReposItem(index, item)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    AppTheme {
//        ReposMainBody()
    }
}
