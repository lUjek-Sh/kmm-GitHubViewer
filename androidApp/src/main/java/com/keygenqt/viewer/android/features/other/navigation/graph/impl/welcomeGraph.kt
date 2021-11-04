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
package com.keygenqt.viewer.android.features.other.navigation.graph.impl

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keygenqt.viewer.android.base.AppActions
import com.keygenqt.viewer.android.features.other.navigation.nav.OtherNav
import com.keygenqt.viewer.android.features.other.ui.actions.WelcomeActions
import com.keygenqt.viewer.android.features.other.ui.screens.welcome.WelcomeScreen

/**
 * NavGraph for [WelcomeScreen]
 */
fun NavGraphBuilder.welcomeGraph(
    appActions: AppActions,
) {
    composable(OtherNav.navWelcome.welcomeScreen.route) {
        WelcomeScreen(viewModel = hiltViewModel()) { event ->
            when (event) {
                is WelcomeActions.ToLogin -> {
                }
            }
        }
    }
}