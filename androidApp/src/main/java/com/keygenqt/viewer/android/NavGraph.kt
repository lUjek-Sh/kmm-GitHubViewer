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
package com.keygenqt.viewer.android

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.keygenqt.viewer.android.base.AppActions
import com.keygenqt.viewer.android.base.LocalViewModel
import com.keygenqt.viewer.android.base.viewModel.AppViewModel
import com.keygenqt.viewer.android.features.followers.navigation.graph.followersNavGraph
import com.keygenqt.viewer.android.features.other.navigation.graph.otherNavGraph
import com.keygenqt.viewer.android.features.other.navigation.nav.OtherNav
import com.keygenqt.viewer.android.features.profile.navigation.graph.profileNavGraph
import com.keygenqt.viewer.android.features.repos.navigation.graph.reposNavGraph
import com.keygenqt.viewer.android.features.stats.navigation.graph.statsNavGraph
import com.keygenqt.viewer.android.menu.MenuBottomBar
import com.keygenqt.viewer.android.menu.MenuTab
import com.keygenqt.viewer.android.utils.ConstantsApp.START_DESTINATION
import com.keygenqt.viewer.android.utils.ListenDestination

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    appViewModel: AppViewModel = LocalViewModel.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    softwareKeyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    // Actions navigation all app
    val appActions = remember(navController) {
        AppActions(navController)
    }

    // Listen change navigation
    ListenDestination.Init(navController) {
        softwareKeyboardController?.hide()
    }

    val context = LocalContext.current
    val bg = MaterialTheme.colorScheme.background.toArgb()
    val isLogin by appViewModel.isLogin.collectAsState(null)

    isLogin?.let {
        LaunchedEffect(lifecycleOwner.lifecycle.currentState == Lifecycle.State.CREATED) {
            // if not login to welcome
            if (!it && ListenDestination.getActionDestination()?.route != OtherNav.navSignIn.signInScreen.route) {
                appActions.toWelcome()
            }
            // remove BG splash
            (context as MainActivity).window.decorView.setBackgroundColor(bg)
            // disable splash
            appViewModel.disableSplash()
        }
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = START_DESTINATION
    ) {
        otherNavGraph(appActions)
        reposNavGraph(appActions)
        followersNavGraph(appActions)
        statsNavGraph(appActions)
        profileNavGraph(appActions)
    }

    // navigation bottom bar
    MenuBottomBar.onClick = { tab ->
        when (tab) {
            MenuTab.REPOS -> appActions.toReposMain()
            MenuTab.FOLLOWERS -> appActions.toFollowersMain()
            // MenuTab.STATS -> appActions.toStatsMain() @todo
            MenuTab.PROFILE -> appActions.toProfileMain()
        }
    }
}
